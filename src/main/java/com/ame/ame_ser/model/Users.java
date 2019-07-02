package com.ame.ame_ser.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author LSQ
 * @date 2019/6/24 14:39
 */
@Entity
@Table(name = "USERS")
@Data
public class Users implements Serializable {

    @Id
    @Column(length = 32)
    private String userId;

    @Column(length = 32)
    private String name;

    @Column(length = 50)
    private String pwd;

    @Column(length = 11)
    private String phone;

    @Column(length = 5)
    private Integer sex;

    @Column(length = 50)
    private String card;

    @Column(length = 100)
    private String position;

    @Column(length = 100)
    private String profession;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 用户 - 角色 多对多映射
     */
    @ManyToMany
    @JoinTable(name="users_roles",joinColumns = @JoinColumn(name = "userId"),inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Roles> rolesList;





}
