package cn.edu.zucc.crabxyj.springbootshiro.shiro;

import cn.edu.zucc.crabxyj.springbootshiro.shiro.filter.CorsAuthenticationFilter;
import cn.edu.zucc.crabxyj.springbootshiro.shiro.filter.JwtFilter;
import cn.edu.zucc.crabxyj.springbootshiro.shiro.filter.PermissionsFilter;
import cn.edu.zucc.crabxyj.springbootshiro.shiro.realm.CustomRealm;
import cn.edu.zucc.crabxyj.springbootshiro.shiro.session.ShiroSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author crabxyj
 * @date 2020/6/2 20:48
 */
@ComponentScan(basePackages = "cn.edu.zucc.crabxyj.springbootshiro.service")
@Slf4j
@Configuration
public class ShiroConfig {
    /**
     * 不加这个注解不生效，具体不详
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 账号密码验证
     */
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

//    public JwtRealm jwtRealm(){
//        return new JwtRealm();
//    }


    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisManager redisManager(){
        return new RedisManager();
    }

    @Bean
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 自定义的 shiro session 缓存管理器，用于跨域等情况下使用 token 进行验证，不依赖于sessionId
     */
    @Bean
    public SessionManager sessionManager(){
        //将我们继承后重写的shiro session 注册
        ShiroSession shiroSession = new ShiroSession();
        // map 管理器 new EnterpriseCacheSessionDAO() 这个好像只能在单一应用内共享
        // redis session 管理
        shiroSession.setSessionDAO(redisSessionDAO());
        return shiroSession;
    }



    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }


    /**
     * 配置Shiro核心 安全管理器 SecurityManager
     * SecurityManager安全管理器：所有与安全有关的操作都会与SecurityManager交互；且它管理着所有Subject；负责与后边介绍的其他组件进行交互。
     * （类似于SpringMVC中的DispatcherServlet控制器）
     * 权限管理，配置主要是Realm的管理认证
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义session 管理器
        securityManager.setSessionManager(sessionManager());
        // 设置redis cache 管理器
        securityManager.setCacheManager(cacheManager());

        //将自定义的realm交给SecurityManager管理
        //一定要用方法导入不能用new 不然无法注入
//      可自定义多个  setRealms(Arrays.asList(customRealm(), jwtRealm()));
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        log.info("ShiroConfig.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //自定义拦截器
        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        filterMap.put("corsAuthenticationFilter",new CorsAuthenticationFilter());
        // 添加自己的过滤器并且取名为jwt
//        filterMap.put("jwt", new JwtFilter());
        filterMap.put("perms",new PermissionsFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

//      配置哪些页面需要受保护.以及访问这些页面需要的权限.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
         // 登陆
        filterChainDefinitionMap.put("/api/auth/login/**", "anon");
        filterChainDefinitionMap.put("/**/*", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 加入注解的使用，不加入这个注解不生效
     * 开启shiro 的AOP注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
