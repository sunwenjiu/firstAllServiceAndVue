package com.ame.ame_ser.service;

import com.ame.ame_ser.model.Users;

/**
 * @Author LSQ
 * @date 2019/6/25 14:47
 */
public interface UsersService {

    Users findByUserName(String userName);
}
