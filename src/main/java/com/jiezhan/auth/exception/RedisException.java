package com.jiezhan.auth.exception;

/**
 * @author: zp
 * @Date: 2019-11-25 15:13
 * @Description:
 */
public class RedisException extends RuntimeException {


    public RedisException(){
        super();
    }

    /**
     * Create a {@code RedisException} with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RedisException(String msg) {
        super(msg);
    }

    /**
     * Create a {@code RedisException} with the specified detail message and nested exception.
     *
     * @param msg the detail message.
     * @param cause the nested exception.
     */
    public RedisException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Create a {@code RedisException} with the specified nested exception.
     *
     * @param cause the nested exception.
     */
    public RedisException(Throwable cause) {
        super(cause);
    }
}
