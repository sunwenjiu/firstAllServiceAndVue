package com.ame.ser.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author LSQ
 * @date 2019/8/5 9:24
 */
@Data
public class TreeVO implements Serializable {

    private String id;

    private String title;

    private String parentId;

    private Boolean expand = true;

    private List<TreeVO> children;
}
