package com.ame.ser.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Description: 每张表的创建时间和修改时间字段
 * Date: 2018-05-02
 * Time: 20:37
 *
 * @author: ycbx
 */
@Data
@MappedSuperclass
@DynamicUpdate
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = -5244519906338925420L;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;

}
