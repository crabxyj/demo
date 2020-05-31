package cn.edu.zucc.crabxyj.springbootcaffeine.dao;

import org.springframework.cache.annotation.Cacheable;

/**
 * @author crabxyj
 * @date 2020/5/31 19:30
 */
public class DaoSpringCaffeine implements Dao{

    /**
     * 注解使用
     * 注解 @Cacheable 触发缓存入口（这里一般放在创建和获取的方法上）
     * 注解 @CacheEvict 触发缓存的eviction（用于删除的方法上）
     * 注解 @CachePut 更新缓存且不影响方法执行（用于修改的方法上，该注解下的方法始终会被执行）
     * 注解 @Caching 将多个缓存组合在一个方法上（该注解可以允许一个方法同时设置多个注解）
     * 注解 @CacheConfig 在类级别设置一些缓存相关的共同配置（与其它缓存配合使用）
     */

    @Override
    @Cacheable(value = "get")
    public String select(String key) {
        return null;
    }

    @Override
    public void update(String key, String value) {

    }

    @Override
    public void insert(String key, String value) {

    }

    @Override
    public void delete(String key) {

    }
}
