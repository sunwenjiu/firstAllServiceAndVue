package com.ame.ser.utils;

import com.ame.ser.enums.CodeEnum;

/**
 * Description: 【工具类】：通过code或message来获取message或code
 *  
 * 一、通过code来获取message
 * 例：EnumUtil.getByCode(patient.getOperationStatus(), OperationStatusEnum.class).getMessage()
 * 参数1：Integer code；参数2：枚举类
 * 
 * 二、通过message来获取code
 * 例：EnumUtil.getByMessage(patientVO.getStatus(), PatientStatusEnum.class).getCode()
 * 参数1：String message; 参数2：枚举类
 *  
 * Date: 2017-10-25
 * Time: 17:44
 *
 * @author: ycbx
 */
public class EnumUtil {
    /**
     * Gets by code.
     * 通过code来获取message
     *
     * @param <T>       the type parameter
     * @param code      the code
     * @param enumClass the enum class
     * @return the by code
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T obj : enumClass.getEnumConstants()) {
            if (code.equals(obj.getCode())) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Gets by message.
     * 通过message来获取code
     *
     * @param <T>       the type parameter
     * @param message   the message
     * @param enumClass the enum class
     * @return the by message
     */
    public static <T extends CodeEnum> T getByMessage(String message, Class<T> enumClass) {
        for (T obj : enumClass.getEnumConstants()) {
            if (message.equals(obj.getMessage())) {
                return obj;
            }
        }
        return null;
    }
}
