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
     * jwtToken过期时间,30min
     */
    public static Long TOKEN_EXPIRE_TIME = 30 * 60L;

    /**
     * token请求头名称
     */
    public static String TOKEN_HEADER_NAME = "token";

    /**
     * 验证码的key值
     */
    public static String RANDOM_CODE_KEY = "RANDOMVALIDATECODEKEY";

    /**
     * jwt 加密盐
     */
    public static String JWT_SALT = "JWTVERIFYSALT";

    /**
     * 验证码
     */
    public static String VERYFY_CODE = "verifycode";
}

