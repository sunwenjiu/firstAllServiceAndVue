package com.ame.ser.vo;

import com.ame.ser.enums.SexEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 事件导出Excel字段
 * @Author LSQ
 * @date 2019/8/29 15:52
 */
@Data
public class EventExcelVO implements Serializable {

    private String eventId;

    private String patientName;

    private String patientNumber;

    private String patientBirthday;

    private String patientSex;

    private String patientDept;

    private String eventType;

    private String eventLevel;

    private String handlerUserName;

    private String verifierUserName;

    private String creatTimeStr;


}
