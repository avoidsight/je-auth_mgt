package com.jiezhan.auth.filter;

import com.alibaba.fastjson.JSONObject;
import com.jiezhan.auth.constant.Constant;
import com.jiezhan.auth.shiro.token.JwtToken;
import com.jiezhan.auth.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: zp
 * @Date: 2019-11-25 16:10
 * @Description:
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    @Resource
    RedisUtil redisUtil;

    /**
     * 执行登录认证
     * @param request ServletRequest
     * @param response ServletResponse
     * @param mappedValue mappedValue
     * @return 是否成功
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String token = ((HttpServletRequest) request).getHeader(Constant.TOKEN_HEADER_NAME);
        if (token != null) {
            try {
                executeLogin(request, response);
                // 刷新token
                refreshCache(token);
                return true;
            } catch (IncorrectCredentialsException e) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                try (PrintWriter writer = response.getWriter()) {
                    JSONObject o = new JSONObject();
                    o.put("msg", e.getMessage());
                    writer.write(o.toString());
                    writer.flush();
                    return false;
                } catch (IOException e1) {
                    log.error(e1.getMessage());
                }
            }
        }
        // 如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    private void refreshCache(String token) {
        redisUtil.expire(token,Constant.TOKEN_EXPIRE_TIME);
    }

    /**
     * 执行登录
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(Constant.TOKEN_HEADER_NAME);
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}