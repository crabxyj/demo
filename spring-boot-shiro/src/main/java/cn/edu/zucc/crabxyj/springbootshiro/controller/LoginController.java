package cn.edu.zucc.crabxyj.springbootshiro.controller;

import cn.edu.zucc.crabxyj.springbootshiro.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2019/12/25 16:31
 */
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class LoginController {

    @Resource(name = "accountServiceImpl")
    private AccountService service;

    @CrossOrigin
    @RequestMapping("/login")
    public String login(String username, String password) throws Exception {
        log.info(String.format("username : %s , password : %s",username,password));

        service.login(username,password);

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
        return "token";
    }
}
