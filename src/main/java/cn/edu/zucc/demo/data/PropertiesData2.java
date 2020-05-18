package cn.edu.zucc.demo.data;

import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author crabxyj
 * @date 2020/5/17 18:49
 */
@ToString
@Component
@Setter
@ConfigurationProperties(prefix = "jdbc")
@PropertySource(value = {"classpath:config.properties"},encoding = "UTF-8")
public class PropertiesData2 {
    private String url;
    private String username;
    private String password;
    private String reset;
}
