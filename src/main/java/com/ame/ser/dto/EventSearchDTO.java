package com.ame.ser.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author LSQ
 * @date 2019/8/26 12:24
 */
@Data
public class EventSearchDTO implements Serializable {

       private String eventType;

       private String eventLevel;

       private String dept;

       private String address;

       private String status;

       private String startTime;

       private String endTime;

       private int pageNumber;

       private int pageSize;

}
