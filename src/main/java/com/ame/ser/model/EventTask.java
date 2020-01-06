package com.ame.ser.model;

import com.ame.ser.utils.PrimaryKeyUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Bob.C.J
 * @Description 事件任务
 * @createTime 2019-08-01 09:42
 */
@Entity
@Table(name = "event_task")
@Data
public class EventTask implements Serializable {

    /**
     * 事件任务Id
     */
    @Id
    @Column(length = 32)
    private  String eventTaskId;

    /**
     * 事件类型Id
     */
    @Column(length = 32)
    private String eventTypeId;

    /**
     * 事件类型名称
     */
    @Column(length = 32)
    private String eventTypeName;

    /**
     * 是否直接上报（默认直报，true:直报 1，false：不直报 0，需要处理）
     */
    private Boolean isAccess;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public EventTask(){}

    public EventTask(EventType eventType) {
        this.eventTaskId = PrimaryKeyUtil.getPrimaryId();
        this.eventTypeId = eventType.getEventTypeId();
        this.eventTypeName = eventType.getName();
        this.createTime = new Date();
        this.isAccess = true;
    }
}
