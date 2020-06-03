package cn.edu.zucc.crabxyj.springbootshiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.session.Session;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.exception.SerializationException;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootShiroApplicationTests {

    @Autowired
    private RedisManager redisManager;

    @Test
    void contextLoads() throws SerializationException {
        ObjectSerializer serializer = new ObjectSerializer();
        StringSerializer ks = new StringSerializer();

        String token = "119e9cd0-7e75-474a-9d0e-1943d8f95409";
        byte[] value = redisManager.get(ks.serialize("shiro:session:" + token));
        Object deserialize = serializer.deserialize(value);
        Session session = (Session) deserialize;

        System.out.println(JSON.toJSONString(session));
    }

}
