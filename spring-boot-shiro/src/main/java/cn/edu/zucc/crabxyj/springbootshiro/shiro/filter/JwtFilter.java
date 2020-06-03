package cn.edu.zucc.crabxyj.springbootshiro.shiro.filter;

import cn.edu.zucc.crabxyj.springbootshiro.shiro.token.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * @author crabxyj
 * @date 2020/6/2 21:13
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    /**
     * token 字段名称
     */
    private static final String AUTH_TOKEN = "authToken";
    /**
     * 执行登录认证
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            AuthenticationException e1 = (AuthenticationException) e;
            log.info("jwt"+e1.getMessage(),e);
            return false;
        }
    }

    /**
     * 登录判断
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 尝试从请求头中获取
        String token = httpServletRequest.getHeader(AUTH_TOKEN);
        if (StringUtils.isEmpty(token)){
            // 如果为空则尝试从请求信息中获取
            token = httpServletRequest.getParameter(AUTH_TOKEN);
        }
        log.info(token);

        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true

        log.info("all right");
        return true;
    }
}
