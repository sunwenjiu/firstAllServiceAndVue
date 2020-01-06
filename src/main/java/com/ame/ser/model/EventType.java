package com.ame.ser.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 事件类型实体类
 * @author  SWJ
 */

@Entity
@Table(name = "eventType")
@Data
public class EventType implements Serializable{
    /**
     * 事件类型ID
     */
    @Id
    @Column(length = 32)
    private  String eventTypeId;

    /**
     * 事件类型名称
     */
    @Column(length = 32)
    private String name;

    /**
     * 父事件类型Id
     */
    @Column(length = 32)
    private String parentId;

    /**
     * 事件类型描述
     */
    @Column(length = 100)
    private String eventTypeDescribe;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

}
