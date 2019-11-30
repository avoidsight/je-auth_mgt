package com.jiezhan.auth.exception;

import com.jiezhan.auth.enums.ErrorType;
import lombok.Getter;

/**
 * @author: zp
 * @Date: 2019-10-12 11:23
 * @Description:
 */
@Getter
public class ServiceException extends RuntimeException{
    private Integer code;

    /**
     * 使用已有的错误类型
     * @param type 枚举类中的错误类型
     */
    public ServiceException(ErrorType type){
        super(type.getMsg());
        this.code = type.getCode();
    }

    /**
     * 自定义错误类型
     * @param code 自定义的错误码
     * @param msg 自定义的错误提示
     */
    public ServiceException(Integer code, String msg){
        super(msg);
        this.code = code;
    }
}
