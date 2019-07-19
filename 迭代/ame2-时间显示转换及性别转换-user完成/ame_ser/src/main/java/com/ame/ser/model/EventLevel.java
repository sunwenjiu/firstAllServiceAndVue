package com.ame.ser.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 事件等级实体类
 * @author SWJ
 */

@Entity
@Table(name = "eventLevel")
@Data
public class EventLevel implements Serializable{

    /**
     * 主ID
     */
    @Id
    @Column(length = 32)
    private String eventLevelId;

    /**
     * 名称
     */
    @Column(length = 32)
    private String name;

    /**
     * 描述
     */
    @Column(length = 100)
    private String eventLevelDescribe;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
}
