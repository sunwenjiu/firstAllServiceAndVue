package com.ame.ser.model;

import com.ame.ser.enums.NotifyStatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Description: 用户信息表
 * Date: 2018-05-25
 * Time: 23:42
 *
 * @author: ycbx
 */
@Data
@Entity
@Table(name = "USER_INFO")
public class UserInfo extends BaseModel {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The User id.
     */
    @Id
    @Column(length = 32)
    private String infoId;
    /**
     * 便签或通知的标题
     */
    @Column(length = 100)
    private String title;
    /**
     * 便签
     */
    @Column(length = 1000)
    private String note;
    /**
     * 通知
     */
    @Column(length = 1000)
    private String notify;
    /**
     * 通知读取状态（0:未读，1:已读）
     */
    private NotifyStatusEnum notifyStatus;
    /**
     * 上次登录时间
     */
    private Date loginTime;
    /**
     * 登录次数
     */
    private Integer loginNumber;
    /**
     * 外键，用户ID
     */
    private String userId;
}
