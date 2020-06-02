package cn.edu.zucc.crabxyj.springbootshiro.dao;

import cn.edu.zucc.crabxyj.springbootshiro.pojo.BeanAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author crabxyj
 * @date 2020/6/2 20:42
 */
@Repository("accountDao")
public interface AccountDao extends BaseMapper<BeanAccount> {

}
