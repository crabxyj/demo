package cn.edu.zucc.crabxyj.springbootcaffeine.dao;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author crabxyj
 * @date 2020/5/31 17:51
 */
public class DaoImpl implements Dao {
    private Map<String,String> map = new ConcurrentHashMap<>(20);
    private Random random = new Random();

    public DaoImpl(Map<String,String> map ){
        if (map!=null){
            this.map.putAll(map);
        }
    }

    @Override
    public String select(String key){
        sleep(50);
        return map.get(key);
    }
    @Override
    public void update(String key,String value){
        sleep(random.nextInt(40));
        map.put(key,value);
    }
    @Override
    public void insert(String key,String value){
        sleep(random.nextInt(40));
        map.put(key,value);
    }
    @Override
    public void delete(String key){
        sleep(random.nextInt(40));
        map.remove(key);
    }

    private void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
