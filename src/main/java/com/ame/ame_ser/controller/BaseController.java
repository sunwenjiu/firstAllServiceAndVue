package com.ame.ame_ser.controller;

import com.ame.ame_ser.controllerEx.ControllerException;
import com.ame.ame_ser.controllerEx.ParameterInvalidException;
import com.ame.ame_ser.serviceEx.TableFieldContentDuplicateException;
import com.ame.ame_ser.serviceEx.ServiceException;
import com.ame.ame_ser.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * controller的父类，用来统一处理异常
 *
 * @author SWJ
 */
public class BaseController {
    protected static final Integer SUCCESS = 200;
    protected static Logger logger =  LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler({ServiceException.class,ControllerException.class})
    public ResultVo<Void> handleException(Throwable e){

        ResultVo<Void> rr= new ResultVo<>();
        rr.setMsg(e.getMessage());

        if(e instanceof TableFieldContentDuplicateException){
            //400-部门重复异常
            rr.setCode(400);

        } else if (e instanceof ParameterInvalidException) {
            // 参数非法异常
            rr.setCode(401);
        }


        return rr;

    }


}
