package com.ame.ser.model;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * Description: 资源表（菜单）
 * Date: 2018-05-03
 * Time: 9:02
 *
 * @author: ycbx
 */
@Entity
@Table(name = "resource")
@Data
public class Resource extends BaseModel {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Res id.
     */
    @Id
    @Column(length = 32)
    private String resId;
    /**
     * 编码
     */
    @Column(unique = true, nullable = false, length = 30)
    private String resCode;
    /**
     * 名称
     */
    @Column(unique = true, nullable = false, length = 50)
    private String resName;
    /**
     * 图标
     */
    @Column(length = 50)
    private String resIcon;
    /**
     * 地址
     */
    @Column(nullable = false, length = 500)
    private String resUrl;
    /**
     * 父菜单
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "none"))
    @NotFound(action = NotFoundAction.IGNORE)
    private Resource parent;
}
