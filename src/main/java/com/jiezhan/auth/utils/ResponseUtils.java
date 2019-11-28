package com.jiezhan.auth.utils;

/**
 * @author: zp
 * @Date: 2019-10-12 11:25
 * @Description:
 */
public class ResponseUtils {
    /**
     * 调用成功
     */
    private static final String SUCCESS = "请求成功！";

    public static <T> Response<T> success(T obj){
        Response<T> res = new Response();
        res.setCode(200);
        res.setData(obj);
        res.setMsg(SUCCESS);
        return res;
    }

    public static Response success(){
        return success(null);
    }

    public static Response error(Integer code, String msg){
        Response res = new Response();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }

}
