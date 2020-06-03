package cn.edu.zucc.crabxyj.springbootshiro.controller;

import cn.edu.zucc.crabxyj.core.exception.AuthException;
import cn.edu.zucc.crabxyj.core.exception.BaseException;
import cn.edu.zucc.crabxyj.core.result.ResultWrap;
import cn.edu.zucc.crabxyj.core.utils.ExUtils;
import cn.edu.zucc.crabxyj.springbootshiro.pojo.BeanAccount;
import cn.edu.zucc.crabxyj.springbootshiro.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author crabxyj
 * @date 2019/12/25 16:31
 */
@RestController
@Slf4j
@ResultWrap
@RequestMapping("/api/auth")
public class LoginController {

    @Resource(name = "accountServiceImpl")
    private AccountService service;

    @RequestMapping("/login")
    public Serializable login(String username, String password){
        log.info(String.format("username : %s , password : %s",username,password));
        return loginMethod(username, password);
    }

    @RequestMapping("/register")
    public Serializable register(String username,String password) throws BaseException {
        BeanAccount account = new BeanAccount().setAccount(username).setPassword(password);
        service.register(account);
       return loginMethod(username, password);
    }

    private Serializable loginMethod(String username,String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token);
        }catch (AuthenticationException e){
            e.printStackTrace();
            return "账号密码错误";
        }catch (AuthorizationException e) {
            e.printStackTrace();
            return "没有权限";
        }
        return subject.getSession().getId();
    }

    @RequestMapping("/logout")
    public void logout() throws AuthException {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            throw ExUtils.exAuth("当前用户未登录");
        }
        subject.logout();
    }

}
