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
public class UserRole extends Model<UserRole> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 角色编号
     */
    private Long roleId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
