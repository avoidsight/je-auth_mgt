package com.jiezhan.auth.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zp
 * @Date: 2019-11-25 15:11
 * @Description:
 */
public class CommonsUtils {
    /**
     * 获取六位数验证码
     * @return 验证码
     */
    public static int getCode(){
        return (int)((Math.random()*9+1)*100000);
    }

    /**
     * 使用md5加密
     * @param password 需要加密的密码
     * @param phoneNumber 手机号
     * @return 返回加密后的密码
     */
    public static String encryptPassword(String password, String phoneNumber){
        return String.valueOf(new SimpleHash("MD5", password, phoneNumber, 1024));
    }

    /**
     * 获取请求域中的UserId
     */
    public static Integer getUserId(HttpServletRequest request){
        return Integer.parseInt(request.getAttribute("userId").toString());
    }
}
