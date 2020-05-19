package cn.edu.zucc.demo.controller;

import cn.edu.zucc.demo.apiversion.ApiVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author crabxyj
 * @date 2020/5/19 12:27
 */
@RestController
@RequestMapping("api")
public class ApiVersionTestController {

    @RequestMapping("/{version}/test")
    @ApiVersion
    public String test01(@PathVariable String version) {
        return "test01 : " + version;
    }

    @RequestMapping("/{version}/test")
    @ApiVersion(2)
    public String test02(@PathVariable String version) {
        return "test02 : " + version;
    }

    @RequestMapping("/{version}/test")
    @ApiVersion(3)
    public String test03(@PathVariable String version) {
        return "test03 : " + version;
    }

    @GetMapping("/v4/test")
    public String test04(@PathVariable String version) {
        return "test04 : " + version;
    }
}
