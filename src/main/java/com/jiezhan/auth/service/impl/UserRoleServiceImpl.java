package com.jiezhan.auth.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jiezhan.auth.enums.RoleEnums;
import com.jiezhan.auth.mapper.UserRoleMapper;
import com.jiezhan.auth.model.entity.UserRole;
import com.jiezhan.auth.service.UserRoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author: zp
 * @Date: 2019-11-25 16:25
 * @Description:
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    @CacheEvict(value = "roles", key = "T(String).valueOf(#userId)")
    public void updateRoleByUserIdAndRoleId(Long userId, Long roleId) {
        if(roleId.equals(RoleEnums.USER.getCode())){
            userRoleMapper.updateDriverToUser(userId, RoleEnums.USER.getCode(), RoleEnums.DRIVER.getCode());
        }else if(roleId.equals(RoleEnums.DRIVER.getCode())){
            userRoleMapper.updateUserToDriver(userId, RoleEnums.USER.getCode(), RoleEnums.DRIVER.getCode());

        }
    }

    @Override
    @Cacheable(value = "roles", key = "T(String).valueOf(#userId)", unless = "#result eq null ")
    public Collection<String> selectRoleNamesByUserId(Integer userId) {
        return userRoleMapper.selectRoleNamesByUserId(userId);
    }
}
