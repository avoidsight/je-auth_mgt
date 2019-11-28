package com.jiezhan.auth.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jiezhan.auth.model.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author group
 * @since 2019-08-19
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据userId获取其所有角色名称
     * @param userId userId
     * @return 所有角色名称
     */
    Collection<String> selectRoleNamesByUserId(@Param("userId") Integer userId);

    /**
     * 更新乘客身份为司机
     * @param userId userId
     * @param userRoleId 乘客角色id
     * @param driverRoleId 司机角色id
     */
    void updateUserToDriver(@Param("userId") Long userId, @Param("userRoleId") Long userRoleId, @Param("driverRoleId") Long driverRoleId);

    /**
     * 更新司机身份为乘客
     * @param userId userId
     * @param userRoleId 乘客角色id
     * @param driverRoleId 司机角色id
     */
    void updateDriverToUser(@Param("userId") Long userId, @Param("userRoleId") Long userRoleId, @Param("driverRoleId") Long driverRoleId);
}
