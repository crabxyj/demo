package cn.edu.zucc.demo.crypto.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author crabxyj
 * @date 2020/5/21 19:42
 * 允许在类与方法上使用，对controller中参数数据进行解密
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DecryptRequest {
    /**
     * 是否对请求body进行解密
     * @return 默认true
     */
    boolean value() default true;
}
