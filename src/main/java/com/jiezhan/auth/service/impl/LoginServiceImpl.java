package com.jiezhan.auth.service.impl;

import com.jiezhan.auth.enums.ErrorType;
import com.jiezhan.auth.enums.LoginType;
import com.jiezhan.auth.exception.ServiceException;
import com.jiezhan.auth.feign.EmployeeFeign;
import com.jiezhan.auth.model.vo.Employee;
import com.jiezhan.auth.model.vo.LoginVo;
import com.jiezhan.auth.model.vo.UserVo;
import com.jiezhan.auth.service.LoginService;
import com.jiezhan.auth.shiro.token.CustomizedToken;
import com.jiezhan.auth.utils.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @author: zp
 * @Date: 2019-11-29 14:45
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    EmployeeFeign employeeFeign;
    @Override
    public String login(LoginVo user) {
        Assert.notNull(user.getAccount(), "用户名不能为空");
        Assert.notNull(user.getPassword(), "密码不能为空");
        Employee employee = employeeFeign.getByAccount(user.getAccount()).getData();
        if(ObjectUtils.isEmpty(employee)) {
            throw new ServiceException(ErrorType.USER_NOT_EXIST);
        }
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        CustomizedToken token;
        // 密码登陆
        token = new CustomizedToken(user.getAccount(),user.getPassword(), LoginType.PASSWORD_LOGIN_TYPE.toString());

        // 4.执行登录方法
        try{
            subject.login(token);
            // 生成jwtToken
            String jwtToken = JwtUtil.sign(employee.getId(),employee.getName(), employee.getDeptId(), user.getPassword());
            return jwtToken;
        }catch (UnknownAccountException e) {
            throw new ServiceException(ErrorType.USER_NOT_EXIST);
        } catch (IncorrectCredentialsException e){
            throw new ServiceException(ErrorType.INCORRECT_PASSWORD);
        }
    }

    @Override
    public UserVo getUserInfo(String token) {
        return JwtUtil.getUserInfo(token);
    }
}
