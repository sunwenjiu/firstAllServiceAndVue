package com.ame.ser.dto;

import com.ame.ser.model.EventTaskUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Bob.C.J
 * @Description
 * @createTime 2019-08-20 10:27
 */
@Data
public class EventTaskDTO implements Serializable {

    private static final long serialVersionUID = 140593394266841466L;

    /**
     * 事件任务Id
     */
    private  String eventTaskId;

    /**
     * 事件类型Id
     */
    private String eventTypeId;

    /**
     * 事件类型名称
     */
    private String eventTypeName;

    /**
     * 是否直接上报（默认直报，true:直报，false：不直报，需要处理）
     */
    private Boolean isAccess;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 事件相关人员列表
     */
    private List<EventTaskUser> eventTaskUsers;


    public EventTaskDTO(){}

    public EventTaskDTO(String eventTaskId, String eventTypeId,
                        String eventTypeName, Boolean isAccess, List<EventTaskUser> eventTaskUsers) {
        this.eventTaskId = eventTaskId;
        this.eventTypeId = eventTypeId;
        this.eventTypeName = eventTypeName;
        this.isAccess = isAccess;
        this.eventTaskUsers = eventTaskUsers;
    }
}
