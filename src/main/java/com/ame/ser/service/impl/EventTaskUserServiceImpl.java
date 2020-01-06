package com.ame.ser.service.impl;

import com.ame.ser.model.EventTaskUser;
import com.ame.ser.repository.EventTaskUserRepository;
import com.ame.ser.service.EventTaskUserService;
import com.ame.ser.service.ex.ParameterInvalidException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Bob.C.J
 * @Description
 * @createTime 2019-08-15 10:21
 */
public class EventTaskUserServiceImpl implements EventTaskUserService {

    @Autowired
    EventTaskUserRepository eventTaskUserRepository;

    /**
     * 获取当前用户参与的所有事件列表
     *
     * @param userId
     * @return: java.util.List<com.ame.ser.model.EventTaskUser>
     * @createTime 2019/8/15 10:21
     */
    @Override
    public List<EventTaskUser> getTasksByUser(String userId) {
        if (userId.isEmpty()){
            throw new ParameterInvalidException("用户ID为空！");
        } else {
            return eventTaskUserRepository.findByUserId(userId);
        }
    }
}
