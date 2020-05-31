package cn.edu.zucc.demo.utils;

/**
 * @author crabxyj
 * @date 2020/5/21 20:22
 */
public class StrUtil {
    public static boolean isEmpty(String s){
        return s == null || "".equals(s);
    }

    public static String getStringWithDefault(String s,String defaultValue){
        return isEmpty(s) ? defaultValue : s;
    }

}
