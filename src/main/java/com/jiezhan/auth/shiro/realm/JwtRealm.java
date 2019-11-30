package com.jiezhan.auth.shiro.realm;

import com.jiezhan.auth.enums.ResultEnums;
import com.jiezhan.auth.model.entity.User;
import com.jiezhan.auth.service.UserRoleService;
import com.jiezhan.auth.service.UserService;
import com.jiezhan.auth.shiro.token.JwtToken;
import com.jiezhan.auth.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author: zp
 * @Date: 2019-11-25 15:56
 * @Description:
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = userService.selectUserByPhone(JwtUtil.getUserId(principals.toString()));

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 添加角色
        authorizationInfo.addRoles(userRoleService.selectRoleNamesByUserId(user.getUserId()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得token
        String phone = JwtUtil.getUserId(token);
        User user = userService.selectUserByPhone(phone);
        if (user == null || !JwtUtil.verify(token, user.getPassword())) {
            throw new IncorrectCredentialsException(ResultEnums.TOKEN_INVALID.getMsg());
        }
        return new SimpleAuthenticationInfo(token, token, "JwtRealm");
    }
}
