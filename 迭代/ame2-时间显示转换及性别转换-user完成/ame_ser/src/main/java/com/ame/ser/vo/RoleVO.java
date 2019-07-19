package com.ame.ser.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 17:26
 *
 * @author: ycbx
 */
@Data
public class RoleVO {
    /**
     * ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 编码
     */
    private String roleCode;
    /**
     * 说明
     */
    private String roleDescription;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 角色权限
     */
    private List permissionList;
}
