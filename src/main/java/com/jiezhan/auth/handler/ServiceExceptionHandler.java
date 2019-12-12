package com.jiezhan.auth.handler;
import com.jiezhan.auth.exception.RedisException;
import com.jiezhan.auth.exception.ServiceException;
import com.jiezhan.auth.utils.Response;
import com.jiezhan.auth.utils.ResponseUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: zp
 * @Date: 2019-10-12 11:24
 * @Description:
 */
@ControllerAdvice
@ResponseBody
public class ServiceExceptionHandler {
    /**
     * ExceptionHandler 相当于controller的 RequestMapping
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return 响应对象
     */
    @ExceptionHandler(ServiceException.class)
    public Response handle(ServiceException se){
        return ResponseUtils.error(se.getCode(),se.getMessage());
    }

    @ExceptionHandler(RedisException.class)
    public Response handleRedis(RedisException re) {
        return ResponseUtils.error(710, "redis 异常。");
    }

}
