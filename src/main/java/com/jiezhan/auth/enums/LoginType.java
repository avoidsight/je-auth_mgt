package com.jiezhan.auth.enums;

/**
 * @author: zp
 * @Date: 2019-11-25 15:02
 * @Description:
 */
public enum LoginType {
    /**
     * 密码登录
     */
    PASSWORD_LOGIN_TYPE("Password"),
    /**
     * 验证码登录
     */
    CODE_LOGIN_TYPE("Code");

    private String type;

    LoginType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}