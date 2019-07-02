package com.ame.ame_ser.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author LSQ
 * @date 2019/6/26 15:01
 */
@Entity
@Table(name = "DATASOURCE")
@Data
public class DataSource implements Serializable {

    @Id
    @Column(length = 32)
    private String dsId;

    @Column(length = 32)
    private String name;

    @Column(length = 50)
    private String dsDesc;

    @Column(length = 32)
    private String dsCode;

    @Column(length = 5)
    private Integer actionStatus;

    @Column(length = 32)
    private String parentId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 资源-权限 多对多映射
     */
    @ManyToMany
    @JoinTable(name = "datasource_authority",joinColumns = @JoinColumn(name = "dsId"),inverseJoinColumns = @JoinColumn(name = "authId"))
    private List<Authority> authorityList;

}
