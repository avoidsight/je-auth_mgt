package com.jiezhan.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jiezhan.auth.constant.Constant;
import com.jiezhan.auth.constant.ConstantRedisKey;
import com.jiezhan.auth.enums.LoginType;
import com.jiezhan.auth.enums.ResultEnums;
import com.jiezhan.auth.enums.RoleEnums;
import com.jiezhan.auth.mapper.UserMapper;
import com.jiezhan.auth.model.Result;
import com.jiezhan.auth.model.entity.User;
import com.jiezhan.auth.service.UserRoleService;
import com.jiezhan.auth.service.UserService;
import com.jiezhan.auth.shiro.token.CustomizedToken;
import com.jiezhan.auth.utils.CommonsUtils;
import com.jiezhan.auth.utils.JwtUtil;
import com.jiezhan.auth.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zp
 * @Date: 2019-11-25 16:25
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    RedisUtil redisUtil;

    @Resource
    UserRoleService userRoleService;

    @Override
    public Result sendRegisterCode(String phone) {
        User user = this.selectUserByPhone(phone);
        if (user != null) {
            return Result.error(ResultEnums.PHONE_ALREADY_EXIST);
        }
        // 这里使用默认值，随机验证码的方法为CommonsUtils.getCode()
        int code = 666666;
        // todo 此处为发送验证码代码
        // 此处为发送验证码
        redisUtil.set(ConstantRedisKey.getRegisterCodeKey(phone), code, Constant.CODE_EXPIRE_TIME);
        return Result.success();
    }

    @Override
    public Result register(String phone, String code) {
        // 1.从缓存中获取验证码
        String redisCode = String.valueOf(redisUtil.get(ConstantRedisKey.getRegisterCodeKey(phone)));
        // 判断登录验证码是否存在
        if (StringUtils.isEmpty(code)) {
            return Result.error(ResultEnums.CODE_NOT_NULL);
        }
        // 判断redis验证码是否存在
        if (StringUtils.isEmpty(redisCode)) {
            return Result.error(ResultEnums.CODE_EXPIRE);
        }
        // 判断两个验证码是否相同
        if(code.equals(redisCode)){
            // 2.执行注册代码 默认密码为手机号后六位
            String encryptPassword = CommonsUtils.encryptPassword(phone.substring(5), phone);
            User user = new User();
            user.setPhone(phone);
            user.setPassword(encryptPassword);
            user.setRegisterTime(new Date());
            insert(user);
            // 3.给该用户添加游客身份
            userRoleService.connectUserRole(user.getUserId().longValue(), RoleEnums.GUEST.getCode());
            return Result.success();
        }else {
            return Result.error(ResultEnums.CODE_ERROR);
        }
    }

    @Override
    public Result sendLoginCode(String phone){
        // 这里使用默认值，随机验证码的方法为CommonsUtils.getCode()
        int code = 666666;
        // todo 此处为发送验证码代码
        // 将验证码加密后存储到redis中
        String encryptCode = CommonsUtils.encryptPassword(String.valueOf(code), phone);
        redisUtil.set(ConstantRedisKey.getLoginCodeKey(phone), encryptCode, Constant.CODE_EXPIRE_TIME);
        return Result.success();
    }


    @Override
    public Result login(String phone, String code, String password, String loginType) {
        User sysUser = this.selectUserByPhone(phone);
        if (sysUser == null) {
            return Result.error(ResultEnums.USER_NOT_EXIST);
        }
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        CustomizedToken token;
        ResultEnums msg;
        // 1.判断登录方式
        if(loginType.equals(LoginType.PASSWORD_LOGIN_TYPE.toString())){
            // 密码登录
            token = new CustomizedToken(phone, password, LoginType.PASSWORD_LOGIN_TYPE.toString());
            msg = ResultEnums.PASSWORD_ERROR;
        }else if(loginType.equals(LoginType.CODE_LOGIN_TYPE.toString())){
            // 验证码登录
            token = new CustomizedToken(phone, code, LoginType.CODE_LOGIN_TYPE.toString());
            msg = ResultEnums.CODE_ERROR;
        } else{
            // 不支持该登录方式
            msg = ResultEnums.NOT_SUPPORTED_LOGIN_MODE;
            return Result.error(msg);
        }
        // 4.执行登录方法
        try{
            subject.login(token);
            Map<String, Object> data = new HashMap<>(1);
            User user = selectUserByPhone(phone);
            // 生成jwtToken
            String jwtToken = JwtUtil.sign(phone, user.getUserId(), user.getPassword());
            // token
            data.put("jwtToken", jwtToken);
            return Result.success(data);
        }catch (UnknownAccountException e) {
            return Result.error(ResultEnums.USERNAME_NOT_EXIST);
        }catch (ExpiredCredentialsException e){
            return Result.error(ResultEnums.CODE_EXPIRE);
        } catch (IncorrectCredentialsException e){
            return Result.error(msg);
        }
    }



}
