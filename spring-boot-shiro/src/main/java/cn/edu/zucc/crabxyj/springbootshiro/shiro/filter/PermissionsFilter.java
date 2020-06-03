package cn.edu.zucc.crabxyj.springbootshiro.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author crabxyj
 * @date 2020/6/2 21:17
 */
public class PermissionsFilter extends AuthorizationFilter {
    /**
     * 最高权限
     */
    private static final String SUPER_ROOT = "超管";

    /**
     * 重写成包含关系,有一个通过即可
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  {
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[])mappedValue;
        if (perms!=null &&perms.length>0){
            if (subject.hasRole(SUPER_ROOT)){
                return true;
            }
            for (String perm : perms){
                if(subject.isPermitted(perm)){
                    return true;
                }
            }
        }
        return false;
    }

}
