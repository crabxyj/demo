package cn.edu.zucc.crabxyj.springbootcaffeine.dao;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.Map;

/**
 * @author crabxyj
 * @date 2020/5/31 18:44
 * 简单实现，如果不想结合spring的话可以通过动态代理实现
 */
public class DaoCaffeine implements Dao{

    private Cache<String,String> cache;
    private DaoImpl dao;

    public DaoCaffeine(Map<String,String> map){
        dao = new DaoImpl(map);
        cache = Caffeine.newBuilder().maximumSize(20).build();
    }


    @Override
    public String select(String key) {
        String value = cache.getIfPresent(key);
        if(value==null){
            value = dao.select(key);
            cache.put(key,value);
        }
        return value;
    }

    @Override
    public void update(String key, String value) {
        cache.invalidate(key);
        dao.update(key,value);
    }

    @Override
    public void insert(String key, String value) {
        dao.insert(key,value);
    }

    @Override
    public void delete(String key) {
        cache.invalidate(key);
        dao.delete(key);
    }
}
