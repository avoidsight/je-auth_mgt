package com.jiezhan.auth.model;

import com.jiezhan.auth.enums.ResultEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zp
 * @Date: 2019-11-25 16:18
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean success;
    private Map<String, Object> data;
    private Integer code;
    private String msg;

    private Result(boolean success){
        this.success = success;
    }

    private Result(boolean success, Map<String, Object> data){
        this.success = success;
        this.data = data;
    }

    private Result(boolean success, Integer code, String msg){
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public static Result success(Map<String, Object> data){
        return new Result(true, data);
    }

    public static Result success(){
        return new Result(true);
    }

    public static Result success(String key, Object value){
        Map<String, Object> data = new HashMap<>(1);
        data.put(key, value);
        return new Result(true, data);
    }

    public static Result error(ResultEnums resultEnums){
        return new Result(false, resultEnums.getCode(), resultEnums.getMsg());
    }

}
