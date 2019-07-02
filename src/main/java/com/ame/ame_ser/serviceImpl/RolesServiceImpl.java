package com.ame.ame_ser.serviceImpl;

import com.ame.ame_ser.dao.RolesDao;
import com.ame.ame_ser.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author LSQ
 * @date 2019/6/25 14:48
 */
@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesDao rolesDao;



}
