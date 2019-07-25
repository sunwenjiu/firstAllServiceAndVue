package com.ame.ser.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @Author LSQ
 * @date 2019/6/25 11:39
 */
public class PrimaryKeyUtil {

    private static Logger logger =  LoggerFactory.getLogger(DateUtil.class);

    /**
     * 获取UUID随机主键
     * @return
     */
    public static String getPrimaryId(){
        String primaryIdStr = null;
        try {
            primaryIdStr = UUID.randomUUID().toString().replaceAll("-","");
        }catch (Exception e){
            logger.error("主键Id获取失败，异常{}",e);
        }
        return primaryIdStr;
    }
}
