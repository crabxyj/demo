package cn.edu.zucc.crabxyj.springbootshiro.service;

import cn.edu.zucc.crabxyj.springbootshiro.dao.AccountDao;
import cn.edu.zucc.crabxyj.springbootshiro.pojo.BeanAccount;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author crabxyj
 * @date 2020/6/2 20:43
 */
@Service("accountServiceImpl")
public class AccountServiceImpl extends ServiceImpl<AccountDao, BeanAccount> implements AccountService {
    @Resource(name = "accountDao")
    private AccountDao dao;

    @Override
    public BeanAccount login(String username, String password) throws Exception {
        BeanAccount account = getByName(username);
        if(account==null|| account.getPassword().equals(password)){
           throw new Exception("账号或密码错误");
        }
        return account;
    }

    public BeanAccount getByName(String username){
        QueryWrapper<BeanAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",username);
        return dao.selectOne(queryWrapper);
    }
}
