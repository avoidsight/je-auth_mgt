package com.jiezhan.auth.controller;

import com.jiezhan.auth.model.Result;
import com.jiezhan.auth.service.UserService;
import com.netflix.client.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: zp
 * @Date: 2019-11-20 10:46
 * @Description:
 */
@Api(value = "auth", tags = "验证")
@RestController
@RequestMapping("/api/v2.0/auth")
public class LoginController {

    @Resource
    private UserService userService;


    /**
     * 发送注册验证码
     * @param phone 手机号
     * @return 发送结果
     */
    @GetMapping("/registerCode")
    public Result sendRegisterCode(@RequestParam String phone) throws ClientException {
        return userService.sendRegisterCode(phone);
    }


    /**
     * 注册
     * @param phone 手机号
     * @param code 验证码
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestParam String phone, @RequestParam String code){
        return userService.register(phone,code);
    }


    /**
     * 发送登录验证码
     * @param phone 手机号
     * @return 发送结果
     */
    @GetMapping("/loginCode")
    public Result sendLoginCode(@RequestParam String phone) throws ClientException {
        return userService.sendLoginCode(phone);
    }

    /**
     * 登录
     * @param phone 手机号
     * @param code 验证码
     * @param password 密码
     * @param loginType 登录方式 Password/Code
     * @return 返回是否登录成功
     */
    @ApiOperation("登陆")
    @PostMapping("/login")
    public Result login(@ApiParam @RequestParam String phone, @ApiParam @RequestParam String code,@ApiParam @RequestParam String password,@ApiParam @RequestParam String loginType){
        return userService.login(phone, code, password, loginType);
    }
}
