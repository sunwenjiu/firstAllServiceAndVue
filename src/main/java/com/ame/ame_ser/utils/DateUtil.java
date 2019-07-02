package com.ame.ame_ser.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  Date日期工具类
 * @Author LSQ
 * @date 2019/6/25 10:49
 */
public class DateUtil {

    private static Logger logger =  LoggerFactory.getLogger(DateUtil.class);

    /**
     * String To Date
     * @param source
     * @param pattern
     * @return
     */
    public static Date stringToDate(String source, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (Exception e) {
            logger.error("String转Date失败，异常{}",e);
        }
        return date;
    }

    /**
     * Date To String
     * @param source
     * @param pattern
     * @return
     */
    public static String DateToString(Date source, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateStr = null;
        try {
            dateStr = simpleDateFormat.format(source);
        }catch (Exception e){
            logger.error("Date转String失败，异常{}",e);
        }
        return dateStr;
    }


    /**
     * 获取输入日期的上一个月
     * @param targetDate
     * @return
     */
    public static Date getPrevMonth(Date targetDate){
        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(targetDate); // 设置为当前时间
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
            targetDate = calendar.getTime();
        }catch (Exception e){
            logger.error("获取上个月Date失败，异常{}",e);
        }
        return targetDate;
    }


    /**
     * 获取输入日期的下一个月
     * @param targetDate
     * @return
     */
    public static Date getNextMonth(Date targetDate){
        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(targetDate); // 设置为当前时间
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为下一个月
            targetDate = calendar.getTime();
        }catch (Exception e){
            logger.error("获取下个月Date失败，异常{}",e);
        }
        return targetDate;
    }

    /**
     * 获取输入日期的上一天
     * @param targetDate
     * @return
     */
    public static Date getPrevDay(Date targetDate){
        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(targetDate); // 设置为当前时间
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1); // 设置为上一个月
            targetDate = calendar.getTime();
        }catch (Exception e){
            logger.error("获取上一天Date失败，异常{}",e);
        }
        return targetDate;
    }


    /**
     * 获取输入日期的下一天
     * @param targetDate
     * @return
     */
    public static Date getNextDay(Date targetDate){
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(targetDate); // 设置为当前时间
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1); // 设置为上一个月
            targetDate = calendar.getTime();
        }catch (Exception e){
            logger.error("获取下一天Date失败，异常{}",e);
        }
        return targetDate;
    }

    /**
     * 出生日期 转 年龄
     * @param birth
     * @return
     */
    public static Integer getAgeByBirth(Date birth){
        Integer age = 0;
        Calendar cal = Calendar.getInstance();
        if(cal.before(birth)){
            return age;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birth);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;
        if(monthNow <= monthBirth){
            if(monthNow == monthBirth){
                if(dayNow < dayBirth){
                    age--;
                }
            }else {
                age--;
            }
        }
        return age;
    }




}
