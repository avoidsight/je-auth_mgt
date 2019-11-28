package com.jiezhan.auth.service;


import com.baomidou.mybatisplus.service.IService;
import com.jiezhan.auth.model.entity.Role;

/**
 * @author: zp
 * @Date: 2019-11-25 16:25
 * @Description:
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据roleId获取RoleName
     * @param roleId roleId
     * @return roleName
     */
    String selectRoleNameById(Long roleId);

}
