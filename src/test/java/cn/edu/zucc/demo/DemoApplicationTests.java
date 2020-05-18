package cn.edu.zucc.demo;

import cn.edu.zucc.demo.data.AppConfig;
import cn.edu.zucc.demo.data.PropertiesData;
import cn.edu.zucc.demo.data.PropertiesData2;
import cn.edu.zucc.demo.data.YmlData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private YmlData ymldata;

    @Autowired
    private PropertiesData proData;
    @Autowired
    private PropertiesData2 proData2;
    @Autowired
    private AppConfig appConfig;
    @Test
    void readConfigTest(){
        System.out.println(proData);
        System.out.println(proData2);
        System.out.println(appConfig);
        System.out.println(ymldata);
    }

}
