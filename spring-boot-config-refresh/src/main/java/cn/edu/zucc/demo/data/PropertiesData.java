package cn.edu.zucc.demo.data;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author crabxyj
 * @date 2020/5/17 18:49
 */
@ToString
@Component
@RefreshScope
@PropertySource(value = {"classpath:config.properties"})
public class PropertiesData {
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.reset}")
    private String reset;
}
