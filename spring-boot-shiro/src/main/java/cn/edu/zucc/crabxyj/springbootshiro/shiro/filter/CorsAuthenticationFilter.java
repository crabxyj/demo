package cn.edu.zucc.crabxyj.springbootshiro.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author crabxyj
 * @date 2020/6/2 21:15
 * 过滤OPTIONS请求，跨域请求支持
 *
 * 继承shiro 的form表单过滤器，对 OPTIONS 请求进行过滤。
 * 前后端分离项目中，由于跨域，会导致复杂请求，即会发送preflighted request，这样会导致在GET／POST等请求之前会先发一个OPTIONS请求，但OPTIONS请求并不带shiro
 * 的'authToken'字段（shiro的SessionId），即OPTIONS请求不能通过shiro验证，会返回未认证的信息。
 * 需要在 shiroConfig 进行注册
 */
public class CorsAuthenticationFilter extends FormAuthenticationFilter {
    /**
     * 直接过滤可以访问的请求类型
     */
    private static final String REQUET_TYPE = "OPTIONS";
    public CorsAuthenticationFilter() {
        super();
    }
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals(REQUET_TYPE)) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        JSONObject result = new JSONObject();
        result.put("code",-1);
        result.put("msg","请先登录系统！");
        writer.write(result.toJSONString());
        writer.close();
        return false;
    }

}
