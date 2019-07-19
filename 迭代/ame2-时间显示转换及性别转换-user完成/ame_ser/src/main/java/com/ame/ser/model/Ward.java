package com.ame.ser.model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 病区实体类
 * @author SWJ
 */

@Entity
@Table(name = "ward")
@Data
public class Ward implements Serializable {

    /**
     * 病区表主ID
     */
    @Id
    @Column(length = 32)
    private String wardId;

    /**
     * 病区名称
     */
    @Column(length = 32)
    private String name;

    /**
     * 病区描述
     */
    @Column(length = 100)
    private String wardDescribe;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}
