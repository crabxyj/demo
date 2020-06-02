package cn.edu.zucc.crabxyj.springbootshiro.shiro.realm;

import cn.edu.zucc.crabxyj.springbootshiro.pojo.BeanAccount;
import cn.edu.zucc.crabxyj.springbootshiro.service.AccountServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2020/6/2 20:48
 * 自定义Realm用于查询用户
 */
public class CustomRealm extends AuthorizingRealm {
    @Resource(name = "accountServiceImpl")
    private AccountServiceImpl service;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户id
        String userName = (String)principalCollection.getPrimaryPrincipal();


        SimpleAuthorizationInfo authorInfo = new SimpleAuthorizationInfo();
//        //添加角色
//        for (BeanRole role : userInfo.getRoles()){
//            authorInfo.addRole(role.getRoleName());
//        }
//        //添加权限
//        for (BeanPermission permission : userInfo.getPermissions()){
//            authorInfo.addStringPermission(permission.getName());
//        }
        return authorInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        if (username == null) {
            return null;
        }

        //获取用户信息
        BeanAccount account = service.getByName(username);

        if (account==null){
            return null;
        }
        // 校验密码
        if (account.getPassword().equals(String.valueOf(token.getPassword()))) {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            return new SimpleAuthenticationInfo(username, account.getPassword(), getName());
        }
        //这里返回后会报出对应异常
        return null;
    }
}
