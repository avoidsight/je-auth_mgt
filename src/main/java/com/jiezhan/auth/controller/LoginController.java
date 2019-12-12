package com.jiezhan.auth.controller;

import com.alibaba.fastjson.JSON;
import com.jiezhan.auth.constant.Constant;
import com.jiezhan.auth.enums.ErrorType;
import com.jiezhan.auth.exception.ServiceException;
import com.jiezhan.auth.feign.EmployeeFeign;
import com.jiezhan.auth.model.vo.LoginVo;
import com.jiezhan.auth.model.vo.UserVo;
import com.jiezhan.auth.service.LoginService;
import com.jiezhan.auth.utils.RandomCodeUtil;
import com.jiezhan.auth.utils.RedisUtil;
import com.jiezhan.auth.utils.Response;
import com.jiezhan.auth.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: zp
 * @Date: 2019-11-29 11:23
 * @Description:
 */
@Api(value = "auth",tags = "登陆")
@RestController
@RequestMapping("/api/v2.0/auth/login")
@Slf4j
public class LoginController {

    @Resource
    RedisUtil redisUtil;

    @Resource
    EmployeeFeign employeeFeign;

    @Resource
    LoginService loginService;

    @GetMapping
    @ApiOperation("获取登陆人信息")
    public Response getEmp(@ApiParam("账号") @RequestParam() String account) {
        return employeeFeign.getByAccount(account);
    }

    @PostMapping
    @ApiOperation("登陆")
    public Response login(@ApiParam("登陆账号密码") @RequestBody LoginVo loginVo) {
        Map tokenUser = loginService.login(loginVo);
        return ResponseUtils.success(tokenUser);
    }


    @GetMapping("/token")
    @ApiOperation("根据token获取user信息")
    public Response<UserVo> getUser(@ApiParam("token") @RequestParam String token){
        UserVo userVo ;
        if(!ObjectUtils.isEmpty(redisUtil.get(token))){
            log.info("redis 里的信息。");
            userVo = JSON.parseObject(redisUtil.get(token).toString(),UserVo.class);
        }else {
            log.info("缓存里的信息。");
            userVo = loginService.getUserInfo(token);
        }
        return ResponseUtils.success(userVo);
    }

    @GetMapping(value = "/getVerifyPic")
    @ApiOperation("获取验证码图片")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomCodeUtil randomValidateCode = new RandomCodeUtil();
            //输出验证码图片方法
            randomValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            throw new ServiceException(ErrorType.VERIFY_PIC_FAILED);
        }
    }

    @GetMapping(value = "/checkVerify")
    @ApiOperation("验证随机码是否正确")
    public Response checkVerify(@RequestParam String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try{
            //从session中获取随机数
            String random = (String) session.getAttribute(Constant.RANDOM_CODE_KEY);
            if (random != null && random.equalsIgnoreCase(code)){
                return ResponseUtils.success();
            }
            // 其他情况下跑出异常
            throw new ServiceException(ErrorType.CODE_VERIFY_FAILED);
        }catch (Exception e){
            throw new ServiceException(ErrorType.CODE_VERIFY_FAILED);
        }
    }

    @PostMapping("/out")
    @ApiOperation("退出登陆")
    public Response logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseUtils.success();
    }

    @GetMapping("/getShiroUser")
    public Response getShiroUser(){
        Object shiroUser = SecurityUtils.getSubject().getPrincipal();
        return ResponseUtils.success(shiroUser);
    }

}
