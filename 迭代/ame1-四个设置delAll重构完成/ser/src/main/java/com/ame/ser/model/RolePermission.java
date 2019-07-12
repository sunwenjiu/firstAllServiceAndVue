package com.ame.ser.model;

import lombok.Data;

import javax.persistence.*;


/**
 * Description: 角色权限关联对象
 * Date: 2018-05-02
 * Time: 23:34
 *
 * @author: ycbx
 */
@Entity
@Table(name = "role_permission")
@Data
public class RolePermission extends BaseModel {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1819231896879859174L;
    /**
     * The Role per id.
     */
    @Id
    @Column(length = 32)
    private String rolePerId;
    /**
     * The Role.
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "none"))
    private Role role;
    /**
     * The Permission.
     */
    @ManyToOne
    @JoinColumn(name = "per_id", nullable = false, foreignKey = @ForeignKey(name = "none"))
    private Permission permission;
}
