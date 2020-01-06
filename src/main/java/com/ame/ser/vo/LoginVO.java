package com.ame.ser.vo;

import lombok.Data;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 19:21
 *
 * @author: ycbx
 */
@Data
public class LoginVO {
    /**
     * Token
     */
    private String token;
    /**
     * 登录用户的信息
     */
    private UserVO user;
}
