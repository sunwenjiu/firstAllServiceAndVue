package com.ame.ser.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Author LSQ
 * @date 2019/6/27 15:31
 */
public class Md5Util {

    /**
     * 密码MD5加密
     * @param source
     * @param salt
     * @param count
     * @return
     */
    public static String pwdByMd5(String source,String salt,Integer count){
        Md5Hash md5Hash = new Md5Hash(source,salt,count);
        return md5Hash.toString();
    }

}
