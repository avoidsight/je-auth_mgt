package com.jiezhan.auth.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author group
 * @since 2019-08-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 角色名称
     */
    private String role;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 是否锁定
     */
    private Integer available;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
