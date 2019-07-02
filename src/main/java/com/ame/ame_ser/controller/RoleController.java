package com.ame.ame_ser.controller;

import com.ame.ame_ser.model.Roles;
import com.ame.ame_ser.service.RolesService;
import com.ame.ame_ser.utils.ResultVoUtil;
import com.ame.ame_ser.vo.ResultVo;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;
import java.util.Date;

/**
 * @Author LSQ
 * @date 2019/6/25 14:56
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RolesService rolesService;

    @RequestMapping("/text1")
    public ResultVo text1(){
        Roles roles = new Roles();
        roles.setName("卡卡西");
        roles.setRoleCode("aa:bb");
        roles.setActionStatus(1);
        roles.setRoleDesc("测试Controller");
        roles.setCreateTime(new Date(System.currentTimeMillis()));
        roles.setUpdateTime(new Date(System.currentTimeMillis()));
        return ResultVoUtil.success(roles);
    }


}
