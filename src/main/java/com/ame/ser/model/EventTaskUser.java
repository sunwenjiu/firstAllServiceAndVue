package com.ame.ser.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Bob.C.J
 * @Description 事件处理人员表
 * @createTime 2019-08-01 10:16
 */
@Entity
@Table(name = "event_task_user")
@Data
public class EventTaskUser implements Serializable {

    private static final long serialVersionUID = 2209545402650529532L;

    /**
     * 设置记录id
     */
    @Id
    @Column(length = 32)
    private String etuId;

    /**
     * 用户id
     */
    @Column(length = 32)
    private String userId;

    /**
     * 处理人员名称
     */
    @Column(length = 32)
    private String userName;

    /**
     * 关联事件
     */
    @Column(length = 32)
    private String eventTaskId;

    /**
     * 事件处理人员类型 (1:直接处理人员 2:结案人员)
     */
    @Column(length = 5)
    private Integer userType;

}