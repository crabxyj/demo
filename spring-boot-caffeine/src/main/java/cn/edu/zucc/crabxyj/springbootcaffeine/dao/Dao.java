package cn.edu.zucc.crabxyj.springbootcaffeine.dao;

/**
 * @author crabxyj
 * @date 2020/5/31 18:00
 */
public interface Dao {
    String select(String key);

    void update(String key,String value);

    void insert(String key,String value);

    void delete(String key);
}
