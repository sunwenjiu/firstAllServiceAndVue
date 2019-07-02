package com.ame.ame_ser.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Json 工具类
 * @Author LSQ
 * @date 2019/6/25 11:17
 */
public class JsonUtil {

    private static Logger logger =  LoggerFactory.getLogger(DateUtil.class);

    private final static String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     *  Object 转 String
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        String result = null;
        try{
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.setDateFormat(JsonUtil.PATTERN).create();
            result = gson.toJson(object);
        }catch (Exception e){
            logger.error("Object转String失败，异常{}",e);
        }
        return result;
    }


    /**
     * String 转 Object
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> t) {
        Gson gson = new GsonBuilder().setDateFormat(JsonUtil.PATTERN).create();
        T ts = gson.fromJson(json, t);
        return ts;
    }

}
