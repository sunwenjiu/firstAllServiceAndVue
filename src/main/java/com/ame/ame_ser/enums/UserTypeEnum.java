package com.ame.ame_ser.enums;

import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @Author LSQ
 */
@Getter
public enum UserTypeEnum implements CodeEnum {

    USER_REPORT(1, "上报用户"), USER_VERIFY(2, "审核用户");

    private final Integer code;

    private final String message;


    UserTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
