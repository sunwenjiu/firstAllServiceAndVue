package com.ame.ame_ser.controllerEx;

/**
 * 参数非法异常
 * @author SWJ
 */
public class ParameterInvalidException extends ControllerException{
    public ParameterInvalidException() {
        super();
    }

    public ParameterInvalidException(String message) {
        super(message);
    }

    public ParameterInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterInvalidException(Throwable cause) {
        super(cause);
    }

    protected ParameterInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
