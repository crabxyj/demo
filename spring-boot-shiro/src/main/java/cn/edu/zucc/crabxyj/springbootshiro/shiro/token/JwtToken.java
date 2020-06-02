package cn.edu.zucc.crabxyj.springbootshiro.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author crabxyj
 * @date 2020/6/2 21:07
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token){
        this.token=token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public String toString() {
        return token;
    }
}