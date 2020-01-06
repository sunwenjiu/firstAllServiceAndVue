package com.ame.ser.dto;

import com.ame.ser.model.EventTask;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author Bob.C.J
 * @Description
 * @createTime 2019-08-15 16:46
 */
@Data
public class EventTaskUserDTO implements Serializable {

    private static final long serialVersionUID = 2759967514582561623L;

    /**
     * 设置记录id
     */
    private String etuId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 关联事件
     */
    private EventTask eventTask;

    /**
     * 事件处理人员类型 (1:直接处理人员 2:结案人员)
     */
    private Integer userType;

    public EventTaskUserDTO(){}

    public EventTaskUserDTO(String userName, EventTask eventTask, Integer userType) {
        this.userName = userName;
        this.eventTask = eventTask;
        this.userType = userType;
    }

    public EventTaskUserDTO(String etuId,String userName, EventTask eventTask, Integer userType) {
        this.etuId = etuId;
        this.userName = userName;
        this.eventTask = eventTask;
        this.userType = userType;
    }
}
