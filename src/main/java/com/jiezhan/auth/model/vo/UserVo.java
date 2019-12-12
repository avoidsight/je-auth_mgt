package com.jiezhan.auth.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zp
 * @Date: 2019-11-29 17:18
 * @Description:
 */
@Data
public class UserVo implements Serializable {
    private String userId;

    private String userName;

    private String userType;

    private String posId;

    private String deptId;

    private String deptName;

    private String tel;

    private String loginUserId;

    private Integer authRange;
}
