package com.ame.ser.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 月份事件等级统计VO
 * @Author LSQ
 * @date 2019/9/1 17:00
 */
@Data
public class ChartEventTimeVO implements Serializable{

    private String name;

    private String type;

    private String stack;

    private List<Integer> data;

}
