package com.ame.ser.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Description: 权限表
 * Date: 2018-05-02
 * Time: 20:37
 *
 * @author: ycbx
 */
@Data
@Entity
@Table(name = "permission")
public class Permission extends BaseModel {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Per id.
     */
    @Id
    @Column(length = 32)
    private String perId;
    /**
     * 权限名称(如:新增，编辑，删除)
     */
    @Column(nullable = false, length = 50)
    private String perName;
    /**
     * 编码(如:user:add,user:update,user:delete)
     */
    @Column(unique = true, nullable = false, length = 50)
    private String perCode;
    /**
     * 说明
     */
    private String perDescription;
    /**
     * 对应的菜单模块对象(如果以一般模块都有增删改查操作来讲，应该是多对多，但从业务上讲，各个模块有不同的操作，所以，设计成多对一)
     */
    @ManyToOne
    @JoinColumn(name = "res_id", nullable = false, foreignKey = @ForeignKey(name = "none"))
    private Resource resource;

}
