package com.ame.ser.enums;

import lombok.Getter;

/**
 * @Author LSQ
 * @date 2019/6/27 14:40
 */
@Getter
public enum LoginStatusEnum implements CodeEnum{

    UN_FIND_USER(1,"用户不存在"),
    USER_PWD_ERROR(2,"用户名或密码错误"),
    USER_LOCK(3,"用户被锁定"),
    LOGIN_COUNT_MAX(4,"登录次数过多"),
    USER_LOGIN_NO(5,"账户禁止登录"),
    USER_LOGIN_ERROR(6,"用户登录失败"),
    OTHER_ERROR(7,"其他异常");


    private final Integer code;

    private final String message;

    LoginStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }


}
