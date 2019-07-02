package com.ame.ame_ser.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author LSQ
 * @date 2019/6/24 15:12
 */
@Entity
@Table(name = "ROLES")
@Data
public class Roles implements Serializable{


    /**
     * The Role id.
     */
    @Id
    @Column(length = 32)
    private String roleId;
    /**
     * 角色名称
     */
    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String roleCode;

    @Column(length = 50)
    private String roleDesc;

    @Column(length = 5)
    private Integer actionStatus;


    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;


    /**
     * 角色-用户 多对多映射
     */
    @ManyToMany
    @JoinTable(name="users_roles",joinColumns = @JoinColumn(name = "roleId"),inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<Users> usersList;

    /**
     * 角色-权限 多对多映射
     */
    @ManyToMany
    @JoinTable(name = "roles_authority",joinColumns = @JoinColumn(name = "roleId"),inverseJoinColumns = @JoinColumn(name = "authId"))
    private List<Authority> authList;


}
