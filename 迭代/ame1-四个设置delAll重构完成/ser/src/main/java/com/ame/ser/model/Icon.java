package com.ame.ser.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Description:
 * Date: 2018-06-21
 * Time: 9:04
 *
 * @author: ycbx
 */
@Data
@Entity
@Table(name = "icon")
public class Icon extends BaseModel {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 294238570030241566L;

    /**
     * id
     */
    @Id
    @Column(length = 32)
    private String id;

    /**
     * 名称
     */
    @Column(unique = true, nullable = false, length = 100)
    private String name;

    /**
     * 编码
     */
    @Column(unique = true, nullable = false, length = 50)
    private String code;

    /**
     * 描述
     */
    @Column(length = 200)
    private String description;
}
