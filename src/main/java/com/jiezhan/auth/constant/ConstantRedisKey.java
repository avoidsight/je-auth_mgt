package com.jiezhan.auth.constant;

/**
 * @author: zp
 * @Date: 2019-11-25 14:53
 * @Description:
 */
public class ConstantRedisKey {

    public static String getRegisterCodeKey(String phone){
        return "REGISTER:CODE:" + phone;
    }

    public static String getLoginCodeKey(String phone){
        return "LOGIN:CODE:" + phone;
    }

}
