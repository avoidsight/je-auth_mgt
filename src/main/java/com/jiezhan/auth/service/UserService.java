package com.jiezhan.auth.service;


import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.jiezhan.auth.model.Result;
import com.jiezhan.auth.model.entity.User;

/**
 * @author: zp
 * @Date: 2019-11-25 16:25
 * @Description:
 */
public interface UserService extends IService<User> {

    /**
     * 发送注册验证码
     * @param phone phone
     * @throws ClientException 阿里云短信异常
     * @return 发送是否成功
     */
    Result sendRegisterCode(String phone) throws ClientException;

    /**
     * 注册
     * @param phone 手机号
     * @param code 验证码
     * @return  注册是否成功
     */
    Result register(String phone, String code);


    /**
     * 发送注册验证码
     * @param phone phone
     * @throws ClientException 阿里云短信异常
     * @return 发送是否成功
     */
    Result sendLoginCode(String phone) throws ClientException;

    /**
     * 登录
     * @param phone 手机号
     * @param code 验证码
     * @param password 密码
     * @param loginType 登录方式 Password/Code
     * @return 登录是否成功
     */
    Result login(String phone, String code, String password, String loginType);


    /**
     * 根据手机号查询用户
     * @param phone 手机号
     * @return 用户
     */
    default User selectUserByPhone(String phone){
        return selectOne(new EntityWrapper<User>().eq("phone", phone).eq("is_deleted",1));
    }



}
