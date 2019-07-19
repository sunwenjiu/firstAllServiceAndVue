package com.ame.ser.vo;

import lombok.Data;

import java.util.List;

/**
 * Description:
 * Date: 2018-05-13
 * Time: 14:02
 *
 * @author: ycbx
 */
@Data
public class ResourceVO {

    /**
     * The Res id.
     */
    private Long resId;
    /**
     * 编码
     */
    private String resCode;
    /**
     * 名称
     */
    private String resName;
    /**
     * 图标
     */
    private String resIcon;
    /**
     * 路径
     */
    private String resUrl;
    /**
     * 下级资源列表.
     */
    private List sonList;
}
