package com.jiezhan.auth.model.vo;

import lombok.Data;

/**
 * @author: zp
 * @Date: 2019-11-28 11:28
 * @Description:
 */
@Data
public class AccountVo {
    /**
     * 用户id
     */
    private String id;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 用户岗位
     */
    private String positionName;

    /**
     * 用户名
     */
    private String loginUserId;

    /**
     * 用户密码（加密）
     */
    private String password;

}
