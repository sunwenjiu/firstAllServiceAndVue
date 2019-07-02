package com.ame.ame_ser.serviceImpl;

import com.ame.ame_ser.dao.UsersDao;
import com.ame.ame_ser.model.Users;
import com.ame.ame_ser.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author LSQ
 * @date 2019/6/25 14:48
 */
@Service
public class UserServiceImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;


    @Override
    public Users findByUserName(String userName) {
        return usersDao.findByName(userName);
    }
}
