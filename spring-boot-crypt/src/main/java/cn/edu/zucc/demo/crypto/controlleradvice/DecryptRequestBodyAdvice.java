package cn.edu.zucc.demo.crypto.controlleradvice;
import cn.edu.zucc.demo.crypto.annotation.DecryptRequest;
import cn.edu.zucc.demo.utils.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import top.jfunc.common.crypto.Crypto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author crabxyj
 * @date 2020/5/21 19:55
 * 请求数据处理类
 * 只对 @RequestBody 参数有效
 */
@ControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice{





    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        boolean encrypt = false;
        boolean classPresentAnno  = methodParameter.getContainingClass().isAnnotationPresent(DecryptRequest.class);
        if(classPresentAnno){
            //类上标注的是否需要解密
            encrypt = methodParameter.getContainingClass().getAnnotation(DecryptRequest.class).value();
            //类不解密，所有都不解密
            if(!encrypt){
                return false;
            }
        }
        boolean methodPresentAnno = Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(DecryptRequest.class);
        if(methodPresentAnno){
            //方法上标注的是否需要解密
            encrypt = methodParameter.getMethod().getAnnotation(DecryptRequest.class).value();
        }
        return encrypt;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return new DecryptHttpInputMessage(inputMessage);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
class DecryptHttpInputMessage implements HttpInputMessage{
    private HttpInputMessage inputMessage;
    @Value("${spring.crypto.request.decrypt.charset:UTF-8}")
    private String charset;
    @Autowired
    @Qualifier("rrCrypto")
    private Crypto crypto;
    DecryptHttpInputMessage(HttpInputMessage inputMessage){
        this.inputMessage = inputMessage;
    }

    @Override
    public InputStream getBody() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputMessage.getBody(), charset);
        String content = IOUtil.inputStreamReaderByReader(inputMessage.getBody() , charset);
        String decryptBody = crypto.decrypt(content, charset);
        return new ByteArrayInputStream(decryptBody.getBytes(charset));
    }

    @Override
    public HttpHeaders getHeaders() {
        return inputMessage.getHeaders();
    }
}