package com.jiezhan.auth.shiro.realm;

import com.jiezhan.auth.shiro.token.CustomizedToken;
import com.jiezhan.auth.shiro.token.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author: zp
 * @Date: 2019-11-25 15:52
 * @Description:
 */
@Slf4j
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("UserModularRealmAuthenticator:method doAuthenticate() 执行 ");
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();

        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        Collection<Realm> typeRealms = new ArrayList<>();

        // 强制转换回自定义的Token
        try{
            JwtToken jwtToken = (JwtToken) authenticationToken;
            for(Realm realm : realms){
                if (realm.getName().contains("Jwt")){
                    typeRealms.add(realm);
                }
            }
            return doSingleRealmAuthentication(typeRealms.iterator().next(), jwtToken);
        }catch (ClassCastException e){
            typeRealms.clear();

            CustomizedToken customizedToken = (CustomizedToken) authenticationToken;
            // 登录类型
            String loginType = customizedToken.getLoginType();

            for (Realm realm : realms) {
                if (realm.getName().contains(loginType)){
                    typeRealms.add(realm);
                }
            }

            // 判断是单Realm还是多Realm
            if(typeRealms.size() == 1){
                return doSingleRealmAuthentication(typeRealms.iterator().next(), customizedToken);
            }else {
                return doMultiRealmAuthentication(typeRealms, customizedToken);
            }
        }

    }
}