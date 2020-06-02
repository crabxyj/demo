package cn.edu.zucc.crabxyj.springbootshiro.shiro.filter;

import cn.edu.zucc.crabxyj.springbootshiro.shiro.token.JwtToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
