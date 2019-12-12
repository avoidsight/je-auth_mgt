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
    private String posName;

    /**
     * 用户名
     */
    private String loginUserId;

    /**
     * 用户密码（加密）
     */
    private String password;

    /**
     * 岗位id
     */
    private String posId;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 操作范围
     */
    private Integer authRange;
}
