package com.ame.ame_ser.serviceEx;

/**
 * 表字段内容重复异常类
 * 部门,用户，等表唯一字段重复异常
 * @author SWJ
 */
public class TableFieldContentDuplicateException extends ServiceException{
    public TableFieldContentDuplicateException() {
        super();
    }

    public TableFieldContentDuplicateException(String message) {
        super(message);
    }

    public TableFieldContentDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableFieldContentDuplicateException(Throwable cause) {
        super(cause);
    }

    protected TableFieldContentDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
