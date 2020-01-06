package com.ame.ser.service;

import com.ame.ser.dto.EventTaskDTO;
import com.ame.ser.dto.EventTaskUserDTO;
import com.ame.ser.model.EventTaskUser;
import com.ame.ser.model.EventType;
import com.ame.ser.model.User;
import com.ame.ser.vo.UserVO;

import java.util.List;

/**
 * @author Bob.C.J
 * @Description 事件处理方式设置业务层
 * @createTime 2019-08-02 17:10
 */
public interface SetEventTaskService {

    /**
     * 获取一个事件类型下的所有处理人员
     *
     * @param eventTypeId
     * @return: java.util.List<com.ame.ser.model.EventTaskUser>
     * @createTime 2019/8/8 11:48
     */
    List<EventTaskUser> getEventTaskUser(String eventTypeId);

    /**
     * 删除事件处理人员
     *
     * @param etuId
     * @return: void
     * @createTime 2019/8/7 11:59
     */
    void deleteEventTaskUser(EventTaskUser etuId);

    /**
     * 新增事件处理人员
     *
     * @param eventTaskUser
     * @return: void
     * @createTime 2019/8/7 12:37
     */
    void addEventTaskUser(EventTaskUser eventTaskUser);

    /**
     * 获取事件任务列表
     *
     * @param 
     * @return: List<EventTask>
     * @createTime 2019/8/12 15:01
     */
    List<EventTaskDTO> getEventTask();

    /**
     * 通过事件类型id判断任务是否直接上报
     *
     * @param eventTypeId
     * @return: boolean
     * @createTime 2019/8/13 15:14
     */
    boolean isAccess(String eventTypeId);

    /**
     * 删除任务类型
     *
     * @param eventTaskId
     * @return: void
     * @createTime 2019/8/15 14:34
     */
    void deleteEventTask(String eventTaskId);

    /**
     * 根据userid查出与用户有关的所有事件
     *
     * @param userId
     * @return: java.util.List<com.ame.ser.model.EventTaskUser>
     * @createTime 2019/8/16 11:57
     */
    List<EventTaskUserDTO> getEventTaskByUser(String userId);

    /**
     * 通过用户名模糊查询用户
     *
     * @param name
     * @return: java.util.List<com.ame.ser.model.User>
     * @createTime 2019/8/22 10:57
     */
    List<UserVO> findUserByNameLike(String name);
}
