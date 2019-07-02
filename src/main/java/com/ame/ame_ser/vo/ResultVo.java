package com.ame.ame_ser.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 结果对象封装VO
 * @Author LSQ
 * @date 2019/6/25 11:27
 */
@Data
public class ResultVo<T> implements Serializable {

    // 返回码
    private Integer code;

    // 返回信息
    private String msg;

    // 返回数据
    private T data;

    /**
     * 构造
     */
    public ResultVo() {
    }
    public ResultVo(Integer code) {
        this.code = code;
    }

    public ResultVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVo(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
