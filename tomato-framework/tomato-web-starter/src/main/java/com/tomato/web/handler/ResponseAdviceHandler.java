package com.tomato.web.handler;

import com.tomato.domain.resp.Resp;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static com.tomato.common.constants.CommonRespCode.INTERNAL_SERVER_ERROR;

/**
 * Controller 返回的内容在 HttpMessageConverter 进行类型转换之前拦截，进行相应的处理操作后，再将结果返回给客户端
 *
 * @author lizhifu
 * @date 2022/12/12
 */
@ControllerAdvice
public class ResponseAdviceHandler implements ResponseBodyAdvice<Object> {
    /**
     * 判断是否要交给 beforeBodyWrite 方法执行，ture：需要；false：不需要
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getMethod() == null) {
            return false;
        }
        // 只拦截返回结果为 CommonResult 类型
        return returnType.getMethod().getReturnType() == Resp.class;
    }

    /**
     * 对 response 进行具体的处理
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof Throwable){
            return Resp.buildFailure(INTERNAL_SERVER_ERROR.code(),INTERNAL_SERVER_ERROR.msg());
        }
        return body;
    }
}
