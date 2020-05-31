package cn.edu.zucc.crabxyj.springbootcaffeine;

import cn.edu.zucc.crabxyj.springbootcaffeine.dao.Dao;
import cn.edu.zucc.crabxyj.springbootcaffeine.dao.DaoCaffeine;
import cn.edu.zucc.crabxyj.springbootcaffeine.dao.DaoImpl;
import org.junit.jupiter.api.Test;

/**
 * @author crabxyj
 * @date 2020/5/31 18:52
 */
class TestWithoutSpring {


    @Test
    void testDao(){
        long l = test(new DaoImpl(null));
        System.out.println("time is "+l);
    }

    @Test
    void testDaoCaffeine(){
        long l = test(new DaoCaffeine(null));
        System.out.println("time is "+l);
    }


    private void init(Dao dao){
        dao.insert("key0","value");
    }

    private long test(Dao dao){
        init(dao);
        System.out.println("init is over");
        long start = System.currentTimeMillis();
        for(int i=0;i<100;i++){
            dao.select("key0");
        }
        long end = System.currentTimeMillis();
        return end - start;

    }
}
