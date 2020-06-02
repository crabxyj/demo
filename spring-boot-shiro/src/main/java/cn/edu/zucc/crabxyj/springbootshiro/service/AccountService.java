package cn.edu.zucc.crabxyj.springbootshiro.service;

import cn.edu.zucc.crabxyj.springbootshiro.pojo.BeanAccount;

/**
 * @author crabxyj
 * @date 2020/6/2 20:43
 */
public interface AccountService {

    BeanAccount login(String username,String password) throws Exception;

}
