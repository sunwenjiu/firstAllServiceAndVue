package com.ame.ame_ser.utils;

import com.ame.ame_ser.vo.ResultVo;

/**
 * 返回 结果工具类
 * @Author LSQ
 * @date 2019/6/25 11:26
 */
public class ResultVoUtil {


    public static ResultVo success(Object object) {
        ResultVo ResultVo = new ResultVo();
        ResultVo.setData(object);
        ResultVo.setCode(0);
        ResultVo.setMsg("成功");
        return ResultVo;
    }


    public static ResultVo success(Object object, String msg) {
        ResultVo ResultVo = new ResultVo();
        ResultVo.setData(object);
        ResultVo.setCode(0);
        ResultVo.setMsg(msg);
        return ResultVo;
    }


    public static ResultVo success() {
        return success(null);
    }


    public static ResultVo error(Integer code, String msg) {
        ResultVo ResultVo = new ResultVo();
        ResultVo.setMsg(msg);
        ResultVo.setCode(code);
        return ResultVo;
    }


    public static ResultVo error(Object object, Integer code, String msg) {
        ResultVo ResultVo = new ResultVo();
        ResultVo.setData(object);
        ResultVo.setMsg(msg);
        ResultVo.setCode(code);
        return ResultVo;
    }


    public static ResultVo success(Integer code, String msg) {
        ResultVo ResultVo = new ResultVo();
        ResultVo.setMsg(msg);
        ResultVo.setCode(code);
        return ResultVo;
    }



}
