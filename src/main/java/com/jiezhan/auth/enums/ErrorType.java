package com.jiezhan.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zp
 * @Date: 2019-10-12 11:22
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum ErrorType {
    /**
     * 错误类型 7xx
     */
    USER_NOT_EXIST(701, "此账号未注册，请重新输入。"),

    INCORRECT_PASSWORD(702, "密码错误，请重新输入。"),

    VERIFY_PIC_FAILED(703, "获取验证码图片失败。"),

    CODE_VERIFY_FAILED(704, "验证码校验失败。")
    ;

    /**
     * 错误码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;
}

