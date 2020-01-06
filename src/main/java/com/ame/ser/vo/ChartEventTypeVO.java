package com.ame.ser.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 事件类型统计VO
 * @Author LSQ
 * @date 2019/9/1 15:17
 */
@Data
public class ChartEventTypeVO implements Serializable{

    private int value;

    private String name;

}
