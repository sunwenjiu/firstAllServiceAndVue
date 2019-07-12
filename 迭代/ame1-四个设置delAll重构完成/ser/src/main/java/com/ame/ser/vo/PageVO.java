package com.ame.ser.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询需要携带实体数据和总条数
 * @author SWJ
 * @param <T>
 */
@Data
public class PageVO<T> implements Serializable {

    private T data;
    private long totalElements; //总条数
    private int totalPages;  //总页数

    public PageVO() {
    }

    public PageVO(T data, long totalElements, int totalPages) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
