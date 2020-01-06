package com.ame.ser.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 事件原因分析图
 * @Author LSQ
 * @date 2019/8/28 15:05
 */
@Data
public class ReasonChartVO implements Serializable{

    private String name;

    private String value;

    private List<ReasonChartVO> children;

}
