package cn.edu.zucc.crabxyj.springbootshiro.service;

import cn.edu.zucc.crabxyj.core.exception.BaseException;
import cn.edu.zucc.crabxyj.core.utils.ExUtils;
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
    public BeanAccount login(BeanAccount account) throws BaseException {
        BeanAccount user = getByName(account.getAccount());
        if(user==null|| user.getPassword().equals(account.getPassword())){
           throw ExUtils.exAuth("账号或密码错误");
        }
        return account;
    }

    @Override
    public BeanAccount register(BeanAccount account) throws BaseException {
        if (getByName(account.getAccount())!=null){
            throw ExUtils.exAuth("当前用户名已存在");
        }
        dao.insert(account);
        return account;
    }

    public BeanAccount getByName(String account){
        QueryWrapper<BeanAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account",account);
        return dao.selectOne(queryWrapper);
    }
}
