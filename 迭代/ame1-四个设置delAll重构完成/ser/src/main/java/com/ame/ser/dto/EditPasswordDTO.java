package com.ame.ser.dto;

import lombok.Data;

/**
 * Description: 修改密码
 * Date: 2018-05-25
 * Time: 13:40
 *
 * @author: ycbx
 */
@Data
public class EditPasswordDTO {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 老密码
     */
    private String oldPass;
    /**
     * 新密码
     */
    private String newPass;
}
