package cn.edu.zucc.demo.utils;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Objects;

/**
 * @author crabxyj
 * @date 2020/5/21 20:18
 */
public class IOUtil {
    public static String inputStreamReaderByReader(InputStream in,String encode){

        encode = StrUtil.getStringWithDefault(encode,"UTF-8");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));
            StringBuilder sb = new StringBuilder();
            String str;
            while((str=reader.readLine())!=null){
                sb.append(str).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
