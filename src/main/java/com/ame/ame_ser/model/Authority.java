package com.ame.ame_ser.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author LSQ
 * @date 2019/6/26 15:00
 */
@Entity
@Table(name = "AUTHORITY")
@Data
public class Authority implements Serializable {

    @Id
    @Column(length = 32)
    private String authId;

    @Column(length = 20)
    private String name;

    @Column(length = 100)
    private String authDesc;

    @Column(length = 32)
    private String authCode;

    @Column(length = 5)
    private Integer actionStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 权限-角色  多对多映射
     */
    @ManyToMany
    @JoinTable(name = "roles_authority",joinColumns = @JoinColumn(name = "authId"),inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Roles> rolesList;


    /**
     *  权限-资源  多对多映射
     */
    @ManyToMany
    @JoinTable(name = "datasource_authority",joinColumns = @JoinColumn(name = "authId"),inverseJoinColumns = @JoinColumn(name = "dsId"))
    private List<DataSource> dataSourceList;


}
