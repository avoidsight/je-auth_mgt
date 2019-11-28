package com.jiezhan.auth.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: zp
 * @Date: 2019-11-25 16:25
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 微信openid
     */
    private String openId;
    /**
     * 真实姓名
     */
    private String username;
    /**
     * 昵称（周先生、刘女士）
     */
    private String nick;
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    /**
     * 身份证Id
     */
    private Integer identityId;
    /**
     * 身份证Id
     */
    private Integer orderQuantity;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 出生日期
     */
    private Date birth;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别（0：女，1：男）
     */
    private Integer sex;
    /**
     * 用户评分
     */
    private Double userScore;
    /**
     * 紧急联系人手机号
     */
    private String emergencyContactPhone;
    /**
     * 紧急联系人关系
     */
    private String emergencyContactRelation;
    /**
     * 上次登录身份（0：用户，1：司机，2：其他）
     */
    private Integer lastLoginIdentity;
    /**
     * 是否完成实名认证(0:未认证,1:认证）
     */
    private Integer isIdentificationApproved;
    /**
     * 是否完成司机认证(0:未认证,1:认证，2:审核中）
     */
    private Integer isDriverApproved;
    /**
     * 注册时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerTime;
    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 删除时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deletedTime;
    /**
     * 是否删除（0：删除，1：否）
     */
    private Integer isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
