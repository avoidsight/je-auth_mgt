package com.jiezhan.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.jiezhan.auth.constant.Constant;
import com.jiezhan.auth.enums.ErrorType;
import com.jiezhan.auth.enums.LoginType;
import com.jiezhan.auth.exception.ServiceException;
import com.jiezhan.auth.feign.EmployeeFeign;
import com.jiezhan.auth.model.vo.AccountVo;
import com.jiezhan.auth.model.vo.LoginVo;
import com.jiezhan.auth.model.vo.UserVo;
import com.jiezhan.auth.service.LoginService;
import com.jiezhan.auth.shiro.token.CustomizedToken;
import com.jiezhan.auth.utils.JwtUtil;
import com.jiezhan.auth.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zp
 * @Date: 2019-11-29 14:45
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    EmployeeFeign employeeFeign;

    @Resource
    RedisUtil redisUtil;

    @Override
    public Map<String,Object> login(LoginVo user) {
        Map<String, Object> tokenUser = new HashMap<>(2);

        Assert.notNull(user.getAccount(), "用户名不能为空");
        Assert.notNull(user.getPassword(), "密码不能为空");
        AccountVo accountVo = employeeFeign.getByAccount(user.getAccount()).getData();
        if(ObjectUtils.isEmpty(accountVo)) {
            throw new ServiceException(ErrorType.USER_NOT_EXIST);
        }
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        CustomizedToken token;
        // 密码登陆
        token = new CustomizedToken(user.getAccount(),user.getPassword(), LoginType.PASSWORD_LOGIN_TYPE.toString());

        // 执行登录方法
        try{
            subject.login(token);
            // 生成jwtToken
            String jwtToken = JwtUtil.sign(accountVo.getId(),accountVo.getName(), accountVo.getPosName(), Constant.JWT_SALT);

            // 把accountVo 转化为 userVo，再把数据放到redis
            UserVo userVo = transfer(accountVo);
            redisUtil.set(jwtToken, JSON.toJSONString(userVo),Constant.TOKEN_EXPIRE_TIME);

            tokenUser.put("accessToken",jwtToken);
            tokenUser.put("user",userVo);
            return tokenUser;
        }catch (UnknownAccountException e) {
            throw new ServiceException(ErrorType.USER_NOT_EXIST);
        } catch (IncorrectCredentialsException e){
            throw new ServiceException(ErrorType.INCORRECT_PASSWORD);
        }
    }

    private UserVo transfer(AccountVo accountVo) {
        UserVo user = new UserVo();
        BeanUtils.copyProperties(accountVo,user);
        user.setUserId(accountVo.getId());
        user.setUserName(accountVo.getName());
        user.setUserType(accountVo.getPosName());
        return user;
    }

    @Override
    public UserVo getUserInfo(String token) {
        return JwtUtil.getUserInfo(token);
    }
}
