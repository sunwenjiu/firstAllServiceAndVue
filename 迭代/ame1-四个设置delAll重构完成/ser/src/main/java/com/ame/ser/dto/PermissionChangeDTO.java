package com.ame.ser.dto;

import com.ame.ser.model.Permission;
import com.ame.ser.model.Role;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 根据角色修改权限请求对象
 * Date: 2018-05-23
 * Time: 17:23
 *
 * @author: ycbx
 */
@Data
public class PermissionChangeDTO implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 3627532138240089475L;

    /**
     * The Role.
     */
    private Role role;

    /**
     * The Permission.
     */
    private Permission permission;

    /**
     * The Status.
     */
    private Boolean status;

}
