package com.ame.ser.model;

import com.ame.ser.enums.SexEnum;
import com.ame.ser.enums.UserStatusEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * Description: 用户表
 * Date: 2018-05-02
 * Time: 20:37
 *
 * @author: ycbx
 */
@Data
@Entity
@Table(name = "USERS")
public class User extends BaseModel {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The User id.
     */
    @Id
    @Column(length = 32)
    private String userId;
    /**
     * 用户名
     */
    @Column(unique = true, nullable = false, length = 20)
    private String userName;
    /**
     * 密码
     */
    @Column(nullable = false, length = 100)
    private String userPassword;
    /**
     * 性别，枚举（0：男，1：女）
     */
    @Column(nullable = false,length = 5)
    private SexEnum userSex;
    /**
     * 电话号码
     */
    @Column(length = 20)
    private String userTel;
    /**
     * 工号
     */
    @Column(length = 20)
    private String userNum;
    /**
     * 真实姓名
     */
    @Column(nullable = false, length = 20)
    private String userRealName;
    /**
     * 状态，枚举(0:锁定,1:启用)
     */
    @Column(nullable = false,length = 5)
    private UserStatusEnum userStatus;
    /**
     * 头像
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String userPhoto;

}
