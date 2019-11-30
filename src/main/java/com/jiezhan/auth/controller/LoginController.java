package com.jiezhan.auth.controller;

import com.jiezhan.auth.feign.EmployeeFeign;
import com.jiezhan.auth.model.vo.LoginVo;
import com.jiezhan.auth.model.vo.UserVo;
import com.jiezhan.auth.service.LoginService;
import com.jiezhan.auth.utils.Response;
import com.jiezhan.auth.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zp
 * @Date: 2019-11-29 11:23
 * @Description:
 */
@Api(value = "auth",tags = "登陆")
@RestController
@RequestMapping("/api/2/0/auth")
public class LoginController {

    @Resource
    EmployeeFeign employeeFeign;

    @Resource
    LoginService loginService;

    @GetMapping
    @ApiOperation("获取登陆人信息")
    public Response getEmp(@ApiParam("账号") @RequestParam() String account) {
        return employeeFeign.getByAccount(account);
    }

    @PostMapping("/login")
    @ApiOperation("登陆")
    public Response login(@ApiParam("登陆账号密码") @RequestBody LoginVo loginVo) {
        Map result = new HashMap(2);
        String token = loginService.login(loginVo);
        UserVo user = loginService.getUserInfo(token);
        result.put("accessToken ",token);
        result.put("user",user);
        return ResponseUtils.success(result);
    }

    @GetMapping("/token")
    @ApiOperation("根据token获取user信息")
    public Response getUser(@ApiParam("token") @RequestParam String token){
        return ResponseUtils.success(loginService.getUserInfo(token));
    }

}
