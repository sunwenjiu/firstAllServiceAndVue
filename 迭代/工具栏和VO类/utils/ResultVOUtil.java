package com.ame.ser.utils;

import com.ame.ser.vo.ResultVO;

/**
 * Description: The type ResultVo util.
 * Date: 2018-03-21
 * Time: 14:18
 *
 * @author: ycbx
 */
public class ResultVOUtil {

    /**
     * Success result VO.
     *
     * @param object the object
     * @return the result VO
     */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");

        return resultVO;
    }

    /**
     * Success result VO.
     *
     * @param object the object
     * @param msg    the msg
     * @return the result VO
     */
    public static ResultVO success(Object object, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg(msg);

        return resultVO;
    }

    /**
     * Success result VO.
     *
     * @return the result VO
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * Error result VO.
     *
     * @param code the code
     * @param msg  the msg
     * @return the result VO
     */
    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }

    /**
     * Error result VO.
     *
     * @param object the object
     * @param code   the code
     * @param msg    the msg
     * @return the result VO
     */
    public static ResultVO error(Object object, Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }

    /**
     * Success result VO.
     *
     * @param code the code
     * @param msg  the msg
     * @return the result VO
     */
    public static ResultVO success(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }

    /**
     * Success result VO.
     *
     * @param object the object
     * @param code   the code
     * @param msg    the msg
     * @return the result VO
     */
    public static ResultVO success(Object object, Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
