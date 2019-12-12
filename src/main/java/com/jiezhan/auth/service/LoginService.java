package com.jiezhan.auth.service;

import com.jiezhan.auth.model.vo.LoginVo;
import com.jiezhan.auth.model.vo.UserVo;

import java.util.Map;

/**
 * @author: zp
 * @Date: 2019-11-29 14:44
 * @Description:
 */
public interface LoginService {
    /**
     * 登陆，返回token
     * @param loginVo
     * @return
     */
    Map login(LoginVo loginVo);

    /**
     * 根据token找到基本user信息
     * @param token token
     * @return
     */
    UserVo getUserInfo(String token);
}
