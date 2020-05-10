package com.parkingmanagement.config;

import com.parkingmanagement.dao.UserDao;
import com.parkingmanagement.entity.system.Permissions;
import com.parkingmanagement.entity.system.Role;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.RoleService;
import com.parkingmanagement.service.UserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String)principals.getPrimaryPrincipal();
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("username",username);
        List<User> userList = userDao.query(queryMap);
        Role role = roleService.getRole(userList.get(0).getRoleId());
        authorizationInfo.addRole(role.getRoleCode());
        for(Permissions p:role.getPermissions()){
            authorizationInfo.addStringPermission(p.getPermissionsCode());
        }
        //CacheUntil.setCacheTree(authorizationInfo);
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        if (StringUtils.isEmpty(username)){
            return null;
        }
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("username",username);
        List<User> users = userDao.query(queryMap);
        if(users == null && !users.isEmpty()){
            return null;
        }
        User user = users.get(0);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}