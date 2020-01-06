package com.ame.ser.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author LSQ
 * @date 2019/7/26 14:56
 */
@Data
public class UserPermissVO implements Serializable {

    private String userId;

    private String userName;

    private List<String> permissionList;
}
