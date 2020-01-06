package com.ame.ser.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Description: 角色表
 * Date: 2018-05-02
 * Time: 20:37
 *
 * @author: ycbx
 */
@Entity
@Table(name = "role")
@Data
public class Role extends BaseModel {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Role id.
     */
    @Id
    @Column(length = 32)
    private String roleId;
    /**
     * 角色名称
     */
    @Column(unique = true, nullable = false, length = 20)
    private String roleName;
    /**
     * 编码
     */
    @Column(unique = true, nullable = false, length = 20)
    private String roleCode;
    /**
     * 说明
     */
    private String roleDescription;

}
