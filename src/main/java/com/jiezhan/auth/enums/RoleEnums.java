package com.jiezhan.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zp
 * @Date: 2019-11-25 15:07
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum  RoleEnums {
    /**
     * 乘客角色
     */
    USER(1L, "user", "乘客角色"),
    /**
     * 司机角色
     */
    DRIVER(2L, "driver", "司机角色"),
    /**
     * 管理员角色
     */
    ADMIN(3L, "admin", "管理员角色"),
    /**
     * 游客角色
     */
    GUEST(4L, "guest", "游客角色");

    private long code;

    private String name;

    private String msg;


}
