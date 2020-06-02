package cn.edu.zucc.crabxyj.springbootshiro.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author crabxyj
 * @date 2020/6/2 20:41
 */
@Data
@TableName("normal_account")
public class BeanAccount {
    @TableId
    private int id;
    private String account;
    private String password;

}
