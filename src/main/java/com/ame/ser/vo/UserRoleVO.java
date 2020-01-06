package com.ame.ser.vo;

import com.ame.ser.enums.SexEnum;
import com.ame.ser.enums.UserStatusEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Date: 2018-05-03
 * Time: 11:34
 *
 * @author: ycbx
 */
@Data
public class UserRoleVO {
    /**
     * ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 性别，枚举（0：男，1：女）
     */
    private SexEnum userSex;
    /**
     * 电话号码
     */
    private String userTel;
    /**
     * 真实姓名
     */
    private String userRealName;
    /**
     * 是否可用
     */
    private UserStatusEnum userStatus;
    /**
     * 头像
     */
    private String userPhoto;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 用户角色
     */
    private List roleList;

}
