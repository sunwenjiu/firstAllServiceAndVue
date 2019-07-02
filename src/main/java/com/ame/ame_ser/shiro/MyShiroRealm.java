package com.ame.ame_ser.shiro;

import com.ame.ame_ser.model.Authority;
import com.ame.ame_ser.model.Roles;
import com.ame.ame_ser.model.Users;
import com.ame.ame_ser.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * shiro 认证 授权配置
 * @Author LSQ
 * @date 2019/6/26 14:19
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    private final String salt = "KMYG";

    @Autowired
    private UsersService usersService;


    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("即将开始用户权限授权");
        Users user = (Users)principalCollection.getPrimaryPrincipal();
        log.info("开始用户权限授权，用户名称为{}",user.getName());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<Roles> roleList = user.getRolesList();
        for(Roles roleItem:roleList){
            authorizationInfo.addRole(roleItem.getRoleCode());
            List<Authority> authorityList =  roleItem.getAuthList();
            for(Authority authItem:authorityList){
                authorizationInfo.addStringPermission(authItem.getAuthCode());
            }
        }
        return authorizationInfo;
    }


    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("即将开始用户登录认证");
        String userName = (String) authenticationToken.getPrincipal();
        log.info("开始用户登录认证，用户名称为{}",userName);
        Users users = usersService.findByUserName(userName);
        if(users == null){
           return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                users,users.getPwd(), ByteSource.Util.bytes(salt),this.getName()
        );
        return authenticationInfo;
    }
}
