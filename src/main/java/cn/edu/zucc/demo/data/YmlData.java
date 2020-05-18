package cn.edu.zucc.demo.data;

import cn.edu.zucc.demo.YamlPropertySourceFactory;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author crabxyj
 * @date 2020/5/17 18:49
 */
@ToString
@Component
@PropertySource(factory  = YamlPropertySourceFactory.class,value = {"classpath:config.yml"})
public class YmlData {
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.reset}")
    private String reset;
}
