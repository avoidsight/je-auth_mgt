package com.jiezhan.auth.enums;

/**
 * @author: zp
 * @Date: 2019-08-17 11:45
 * @Description:
 */
public enum StatusEnum {
    /**
     * 正常数据
     */
    NORMAL("normal"),
    /**
     * 已删除数据
     */
    DELETE("delete"),
    /**
     * 未保存数据
     */
    UNSAVED("unsaved"),
    /**
     * 在职
     */
    ON("在职"),
    /**
     * 离职
     */
    OFF("离职")
    ;

    private final String value;

    StatusEnum(String value){
        this.value = value;
    }

    public String value() {
        return value;
    }
}