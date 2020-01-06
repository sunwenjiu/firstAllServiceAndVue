package com.ame.ser.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 首页标题栏相关统计VO
 * @Author LSQ
 * @date 2019/9/1 22:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartTitleVO {

    private String title;

    private String icon;

    private String count;

    private String color;


}
