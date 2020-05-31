package cn.edu.zucc.crabxyj.springbootcaffeine.dao;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author crabxyj
 * @date 2020/5/31 19:45
 */
@Configuration
@EnableCaching
public class CacheConfig {
    /**
     * 默认最大数量
     */
    public static final int DEFAULT_MAXSIZE = 50000;
    /**
     * 默认过期时间
     */
    public static final int DEFAULT_TTL = 10;

    @Bean
    public CacheManager caffeineCacheManager(){
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = new ArrayList<>();
        for(Caches c : Caches.values()){
            caches.add(new CaffeineCache(c.name(),
                    Caffeine.newBuilder()
                            .recordStats()
                            .expireAfterWrite(c.getTtl(), TimeUnit.SECONDS)
                            .maximumSize(c.getMaxSize())
                            .build())
            );
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    /**
     * 指定cache的基本参数
     */
    public enum Caches{
        /**
         * 有效期5秒
         */
        getPersonById(5),
        /**
         * 缺省10秒
         */
        getSomething,
        /**
         * 5分钟，最大容量1000
         */
        getOtherthing(300, 1000);
        Caches(){
        }
        Caches(int ttl){
            this.ttl = ttl;
        }
        Caches(int ttl,int maxSize){
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

        private int maxSize = DEFAULT_MAXSIZE;
        private int ttl = DEFAULT_TTL;
        public int getMaxSize(){
            return this.maxSize;
        }
        public int getTtl(){
            return this.ttl;
        }
    }

}
