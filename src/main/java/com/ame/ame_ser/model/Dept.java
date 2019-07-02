package com.ame.ame_ser.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门或科室实体类
 * @author SWJ
 *
 */

@Entity
@Table(name = "dept")
@Data
public class Dept implements Serializable {

    /**
     * 科室/部门ID
     */
    @Id
    @Column(length = 32)
    private  String deptId;

    /**
     * 科室名称
     */
    @Column(length = 32)
    private String name;

    /**
     * 部门描述
     */
    @Column(length = 100)
    private String deptDescribe;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

}
