package com.ame.ser.service.impl;

import com.ame.ser.dto.EventTaskDTO;
import com.ame.ser.dto.EventTaskUserDTO;
import com.ame.ser.model.EventTask;
import com.ame.ser.model.EventTaskUser;
import com.ame.ser.model.EventType;
import com.ame.ser.model.User;
import com.ame.ser.repository.EventTaskRepository;
import com.ame.ser.repository.EventTaskUserRepository;
import com.ame.ser.repository.EventTypeRepository;
import com.ame.ser.repository.UserRepository;
import com.ame.ser.service.EventService;
import com.ame.ser.service.SetEventTaskService;
import com.ame.ser.service.ex.ParameterInvalidException;
import com.ame.ser.utils.PrimaryKeyUtil;
import com.ame.ser.vo.UserVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.xwpf.usermodel.TOC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Bob.C.J
 * @Description 事件处理方式
 * @createTime 2019-08-07 11:54
 */
@Service
public class SetEventTaskServiceImpl implements SetEventTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetEventTaskServiceImpl.class);

    @Autowired
    EventTaskUserRepository eventTaskUserRepository;

    @Autowired
    EventTaskRepository eventTaskRepository;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventService eventService;

    /**
     * 获取一个事件类型下的所有处理人员
     *
     * @param eventTypeId
     * @return: java.util.List<com.ame.ser.model.EventTaskUser>
     * @createTime 2019/8/8 11:48
     */
    @Override
    public List<EventTaskUser> getEventTaskUser(String eventTypeId) {
        List<EventTaskUser> eventTaskUsers;
        Optional<EventTask> eventTask = eventTaskRepository.findByEventTypeId(eventTypeId);
        if (eventTask.isPresent()) {
            EventTask et = eventTask.get();
            eventTaskUsers = eventTaskUserRepository.findByEventTaskId(et.getEventTaskId());
            return eventTaskUsers;
        } else {
            throw new ParameterInvalidException("参数错误，没有对应事件任务！");
        }
    }

    /**
     * 删除事件处理人员
     *
     * @param eventTaskUser
     * @return: void
     * @createTime 2019/8/7 11:59
     */
    @Override
    @Transactional
    public void deleteEventTaskUser(EventTaskUser eventTaskUser) {
        Optional<EventTaskUser> etu = eventTaskUserRepository.findById(eventTaskUser.getEtuId());
        if (etu.isPresent()) {
            EventTask eventTask = eventTaskRepository.findByEventTaskId(etu.get().getEventTaskId());
            if (eventTaskUserRepository.findByEventTaskIdAndUserType(eventTask.getEventTaskId(),1).size() < 2) {
                eventTaskUserRepository.delete(eventTaskUser);
                eventTask.setIsAccess(true);
                eventTaskRepository.save(eventTask);
            } else {
                eventTaskUserRepository.delete(eventTaskUser);
            }
        } else {
            throw new ParameterInvalidException("传入的参数有误，数据不存在");
        }
    }

    /**
     * 新增事件处理人员
     *
     * @param eventTaskUser
     * @return: void
     * @createTime 2019/8/7 15:09
     */
    @Override
    @Transactional
    public void addEventTaskUser(EventTaskUser eventTaskUser) {
        System.out.println(ToStringBuilder.reflectionToString(eventTaskUser, ToStringStyle.JSON_STYLE));
        Optional<EventTask> byId = eventTaskRepository.findById(eventTaskUser.getEventTaskId());
        if (byId.isPresent()) {
            if (eventService.getCountByEventTypeOneClassIdOnProcessed(byId.get().getEventTypeId()) > 0L && eventTaskUser.getUserType() == 1) {
                throw new ParameterInvalidException("该类型下有待结案状态的事件，无法添加处理人！");
            } else {
                EventTask eventTask = byId.get();
                if (eventTask.getIsAccess() && eventTaskUser.getUserType() == 1) {
                    eventTask.setIsAccess(false);
                    eventTaskRepository.save(eventTask);
                }
                eventTaskUser.setEtuId(PrimaryKeyUtil.getPrimaryId());
                eventTaskUserRepository.save(eventTaskUser);
            }
        } else throw new ParameterInvalidException("没有对应的事件类型");
    }

    /**
     * 获取事件任务列表
     *
     * @return: List<EventTask>
     * @createTime 2019/8/12 15:01
     */
    @Override
    public List<EventTaskDTO> getEventTask() {
        List<EventTask> eventTasks = eventTaskRepository.findAll();
        List<EventTaskDTO> eventTaskDTOS = new ArrayList<>();
        for (EventTask eventTask : eventTasks) {
            List<EventTaskUser> eventTaskUsers = eventTaskUserRepository.findByEventTaskId(eventTask.getEventTaskId());
            EventTaskDTO eventTaskDTO;
            if (!eventTaskUsers.isEmpty()) {
                eventTaskDTO = new EventTaskDTO(eventTask.getEventTaskId(), eventTask.getEventTypeId(),
                        eventTask.getEventTypeName(), eventTask.getIsAccess(), eventTaskUsers);
            } else {
                eventTaskDTO = new EventTaskDTO(eventTask.getEventTaskId(), eventTask.getEventTypeId(),
                        eventTask.getEventTypeName(), eventTask.getIsAccess(), null);
            }
            eventTaskDTOS.add(eventTaskDTO);
        }
        return eventTaskDTOS;
    }

    /**
     * 通过事件类型id判断任务是否直接上报
     *
     * @param eventTypeId
     * @return: boolean
     * @createTime 2019/8/13 15:14
     */
    @Override
    public boolean isAccess(String eventTypeId) {
        Optional<EventTask> eventTask = eventTaskRepository.findByEventTypeId(eventTypeId);
        if (eventTask.isPresent()) {
            return eventTask.get().getIsAccess();
        } else {
            throw new ParameterInvalidException("传入的参数有误，数据不存在");
        }
    }

    /**
     * 删除任务类型
     *
     * @param eventTaskId
     * @return: void
     * @createTime 2019/8/15 14:34
     */
    @Override
    @Transactional
    public void deleteEventTask(String eventTaskId) {
        eventTaskRepository.deleteByEventTaskId(eventTaskId);
        eventTaskUserRepository.deleteByEventTaskId(eventTaskId);
    }

    /**
     * 根据userId查出与用户有关的所有事件
     *
     * @param userId
     * @return: java.util.List<com.ame.ser.model.EventTaskUser>
     * @createTime 2019/8/16 11:57
     */
    @Override
    public List<EventTaskUserDTO> getEventTaskByUser(String userId) {
        List<EventTaskUserDTO> eventTaskUserDTOS = new ArrayList<>();
        List<EventTaskUser> byUserId = eventTaskUserRepository.findByUserId(userId);
        for (EventTaskUser eventTaskUser : byUserId) {
            Optional<EventTask> byId = eventTaskRepository.findById(eventTaskUser.getEventTaskId());
            if (byId.isPresent()) {
                EventTaskUserDTO eventTaskUserDTO = new EventTaskUserDTO(eventTaskUser.getEtuId(), eventTaskUser.getUserName(), byId.get(), eventTaskUser.getUserType());
                eventTaskUserDTOS.add(eventTaskUserDTO);
            } else {
                throw new ParameterInvalidException("参数错误，查询eventTask失败！");
            }
        }
        return eventTaskUserDTOS;
    }

    /**
     * 通过用户名模糊查询用户
     *
     * @param name
     * @return: java.util.List<com.ame.ser.model.User>
     * @createTime 2019/8/22 10:57
     */
    @Override
    public List<UserVO> findUserByNameLike(String name) {
        List<User> users = userRepository.findByUserNameLike("%" + name + "%");
        List<UserVO> userVOS = new ArrayList<>();
        for (User user : users) {
            UserVO userVO = new UserVO();
            userVO.setUserId(user.getUserId());
            userVO.setUserName(user.getUserName());
            userVO.setUserSex(user.getUserSex());
            userVO.setUserTel(user.getUserTel());
            userVO.setUserRealName(user.getUserRealName());
            userVO.setUserStatus(user.getUserStatus());
            userVOS.add(userVO);
        }
        return userVOS;
    }
}
