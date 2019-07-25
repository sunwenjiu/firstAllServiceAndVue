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
     * 事件类型名称名称
     */
    @Column(length = 32)
    private String name;

    /**
     * 事件类型名称名称
     */
    @Column(length = 32)
    private String parentId;

    /**
     * 类型描述描述
     */
    @Column(length = 100)
    private String eventTypeDescribe;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;


}
