package com.tomato.web.core.handler;

import com.tomato.common.resp.Resp;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

import static com.tomato.common.constants.CommonRespCode.INTERNAL_SERVER_ERROR;

/**
 * Controller 返回的内容在 HttpMessageConverter 进行类型转换之前拦截，进行相应的处理操作后，再将结果返回给客户端
 *
 * @author lizhifu
 * @since  2022/12/12
 */
@ControllerAdvice
public class ResponseAdviceHandler implements ResponseBodyAdvice<Object> {
    @Resource
    private Tracer tracer;
    /**
     * 判断是否要交给 beforeBodyWrite 方法执行，ture：需要；false：不需要
     * @param returnType 返回值类型
     * @param converterType 转换类型
     * @return 是否需要执行
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
     * @param body 返回值
     * @param returnType 返回值类型
     * @param selectedContentType 返回类型
     * @param selectedConverterType 转换类型
     * @param request 请求
     * @param response 响应
     * @return 处理后的结果
     */
    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter returnType, @NotNull MediaType selectedContentType, @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response) {
        if(body instanceof Throwable){
            return Resp.buildFailure(INTERNAL_SERVER_ERROR.code(),INTERNAL_SERVER_ERROR.msg());
        }
        if (body instanceof Resp resp) {
            Span span = tracer.currentSpan();
            if (Objects.nonNull(span)) {
                resp.setTraceId(span.context().traceId());
            }
            return resp;
        }
        return body;
    }
}
