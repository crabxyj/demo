package cn.edu.zucc.crabxyj.springbootshiro.controller;

import cn.edu.zucc.crabxyj.core.result.ResultWrap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author crabxyj
 * @date 2020/6/3 13:15
 */
@RestController
@ResultWrap
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "success";
    }
}
