package com.jiezhan.auth.service;


import com.baomidou.mybatisplus.service.IService;
import com.jiezhan.auth.model.entity.UserRole;

import java.util.Collection;

/**
 * @author: zp
 * @Date: 2019-11-25 16:25
 * @Description:
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 如果为乘客身份更新为司机身份，反之亦然
     * @param userId userId
     * @param roleId roleId
     */
    void updateRoleByUserIdAndRoleId(Long userId, Long roleId);


    /**
     * 根据userId获取所有roleId
     * @param userId userId
     * @return roleIds
     */
    Collection<String> selectRoleNamesByUserId(Integer userId);


    /**
     * 关联用户与角色
     * @param userId 用户id
     * @param roleId 角色id
     */
    default void connectUserRole(Long userId, Long roleId){
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        insert(userRole);
    }
}
