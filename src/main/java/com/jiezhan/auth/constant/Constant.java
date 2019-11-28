package com.jiezhan.auth.constant;

/**
 * @author: zp
 * @Date: 2019-11-25 14:52
 * @Description:
 */
public class Constant {

    /**
     * 验证码过期时间 此处为五分钟
     */
    public static Integer CODE_EXPIRE_TIME = 60 * 5;

    /**
     * jwtToken过期时间
     */
    public static Long TOKEN_EXPIRE_TIME =  60 * 1000L;

    /**
     * token请求头名称
     */
    public static String TOKEN_HEADER_NAME = "authorization";
}

