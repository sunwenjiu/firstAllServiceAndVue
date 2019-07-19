package com.ame.ser.config;

import com.ame.ser.enums.UserStatusEnum;
import com.ame.ser.model.Permission;
import com.ame.ser.model.User;
import com.ame.ser.service.UserService;
import com.ame.ser.vo.ResultVO;
import com.ame.ser.vo.RoleVO;
import com.ame.ser.vo.UserRoleVO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description: 自定义权限匹配和账号密码匹配
 * Date: 2018-05-04
 * Time: 10:01
 *
 * @author: ycbx
 */
public class ShiroRealm extends AuthorizingRealm {

    /**
     * The User service.
     */
    @Autowired
    private UserService userService;

    /**
     * 授权过程
     *
     * @param principalCollection the principal collection
     * @return authorization info
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userName = (String) principalCollection.getPrimaryPrincipal();

        ResultVO resultVO = userService.findByUserName(userName);
        UserRoleVO userRoleVO = new UserRoleVO();
        BeanUtils.copyProperties(resultVO.getData(), userRoleVO);

        for (Object obj : userRoleVO.getRoleList()) {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(obj, roleVO);

            authorizationInfo.addRole(roleVO.getRoleCode());

            for (Object object : roleVO.getPermissionList()) {
                Permission permission = new Permission();
                BeanUtils.copyProperties(object, permission);

                authorizationInfo.addStringPermission(permission.getPerCode());
            }

        }

        return authorizationInfo;

    }


    /**
     * 身份认证的，也就是说验证用户输入的账号和密码是否正确。
     *
     * @param token the token
     * @return authentication info
     * @throws AuthenticationException the authentication exception
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        //获取用户输入的用户名
        String userName = (String) token.getPrincipal();

        //通过用户名到数据库中获取凭证
        User user = userService.findByName(userName);
        if (user == null) {
            return null;
        }
        if (user.getUserStatus() == UserStatusEnum.IS_LOCK) {
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(),
                user.getUserPassword(),
                ByteSource.Util.bytes(user.getUserName()),
                this.getName()
        );

        return authenticationInfo;
    }

    /**
     * 计算加盐的密码
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //本系统用登录名userName作盐,散列两次
        Md5Hash md5Hash = new Md5Hash(new Md5Hash("123456", "ycbx"));
        System.out.println(md5Hash.toString());
    }


}
