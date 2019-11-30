package com.jiezhan.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jiezhan.auth.constant.Constant;
import com.jiezhan.auth.model.vo.UserVo;

import java.util.Date;

/**
 * @author: zp
 * @Date: 2019-11-25 15:11
 * @Description:
 */
public class JwtUtil {
    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", getUserId(token))
                    .withClaim("userName",getUserName(token))
                    .withClaim("userType", getUserType(token))
                    .build();
            // 效验TOKEN
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserType(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userType").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     *
     * @param userId
     * @param userName
     * @param userType
     * @param secret
     * @return 加密的token
     */
    public static String sign(String userId,String userName, String userType, String secret) {
        Date date = new Date(System.currentTimeMillis() + Constant.TOKEN_EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("userName",userName)
                .withClaim("userType", userType)
                .withExpiresAt(date)
                .sign(algorithm);

    }

    /**
     * 根据token获取user信息
     * @param token
     * @return
     */
    public static UserVo getUserInfo(String token) {
        try {
            UserVo user = new UserVo();

            user.setUserId(getUserId(token));
            user.setUserName(getUserName(token));
            user.setUserType(getUserType(token));
            return user;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
