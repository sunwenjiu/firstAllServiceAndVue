package com.ame.ser.service;

import com.ame.ser.model.EventTaskUser;

import java.util.List;

/**
 * @author Bob.C.J
 * @Description
 * @createTime 2019-08-15 10:18
 */
public interface EventTaskUserService {

    /**
     * 获取当前用户参与的所有事件列表
     *
     * @param userId
     * @return: java.util.List<com.ame.ser.model.EventTaskUser>
     * @createTime 2019/8/15 10:21
     */
    List<EventTaskUser> getTasksByUser(String userId);
}
