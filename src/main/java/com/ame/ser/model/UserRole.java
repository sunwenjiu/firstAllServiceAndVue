package com.ame.ser.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Description: 用户与角色关联对象
 * Date: 2018-05-02
 * Time: 23:32
 *
 * @author: ycbx
 */
@Entity
@Table(name = "user_role")
@Data
public class UserRole extends BaseModel {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1410856999622821657L;
    /**
     * The User role id.
     */
    @Id
    @Column(length = 32)
    private String userRoleId;
    /**
     * The User.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "none"))
    private User user;
    /**
     * The Role.
     */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "none"))
    private Role role;
}
