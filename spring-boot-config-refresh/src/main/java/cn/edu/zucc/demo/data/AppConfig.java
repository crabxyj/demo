package cn.edu.zucc.demo.data;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author crabxyj
 * @date 2020/5/18 9:59
 */
@ToString
@Component
public class AppConfig {
    @Value("${biz.name}")
    private String name;
    @Value("${biz.unset:default}")
    private String unset;
}
