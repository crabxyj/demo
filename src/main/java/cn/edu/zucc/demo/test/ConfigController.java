package cn.edu.zucc.demo.test;

import cn.edu.zucc.demo.data.PropertiesData;
import cn.edu.zucc.demo.data.PropertiesData2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author crabxyj
 * @date 2020/5/17 18:23
 */
@RestController
public class ConfigController {

    @Autowired
    private PropertiesData data;
    @Autowired
    private PropertiesData2 proData2;
    @GetMapping("/show")
    public String show(){
        return data.toString()+"\n"+proData2.toString();
    }

}
