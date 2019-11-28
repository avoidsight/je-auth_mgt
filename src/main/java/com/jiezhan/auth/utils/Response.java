package com.jiezhan.auth.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zp
 * @Date: 2019-08-06 12:01
 * @Description:
 */
@Data
public class Response<T> implements Serializable {
    /**
     * 返回码
     */
    @ApiModelProperty(value = "返回码",example = "200")
    private int code ;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回描述
     */
    private String msg ;

    public Response(){
        this.code = 200;
        this.msg = "请求成功！";
    }

    public Response(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public Response(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Response(T data){
        this();
        this.data = data;
    }

    public void success(T data){
        this.data = data;
    }

    public void failed(){
        this.code = 500;
        this.msg = "调用出错！";
    }

}
