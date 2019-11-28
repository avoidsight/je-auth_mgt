package com.jiezhan.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jiezhan.auth.mapper.RoleMapper;
import com.jiezhan.auth.model.entity.Role;
import com.jiezhan.auth.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author: zp
 * @Date: 2019-11-25 16:25
 * @Description:
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public String selectRoleNameById(Long roleId) {
        return selectOne(new EntityWrapper<Role>().eq("id", roleId).eq("available", 0)).getRole();
    }
}
