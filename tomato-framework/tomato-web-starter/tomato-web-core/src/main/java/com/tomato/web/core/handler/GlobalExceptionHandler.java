package com.tomato.web.core.handler;

import com.tomato.common.exception.BusinessException;
import com.tomato.common.resp.Resp;
import com.tomato.common.constants.CommonRespCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static com.tomato.common.constants.CommonRespCode.INTERNAL_SERVER_ERROR;

/**
 * 全局异常处理器|
 * <p>全局拦截异常注解:@RestControllerAdvice = @ControllerAdvice + @ResponseBody</p>
 * @author lizhifu
 * @since  2022/12/7
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 业务异常处理
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Resp<Void> businessExceptionHandler(HttpServletRequest request,BusinessException ex) {
        if (ex.getCause() != null) {
            log.error("全局异常处理器|BusinessException|[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), ex, ex.getCause());
            return Resp.buildFailure(CommonRespCode.INTERNAL_SERVER_ERROR.code(), ex.getMessage());
        }
        log.error("全局异常处理器|BusinessException|[{}] {} [ex] {}", request.getMethod(), request.getRequestURL().toString(), ex.toString());
        return Resp.buildFailure(CommonRespCode.INTERNAL_SERVER_ERROR.code(), ex.getMessage());
    }
    /**
     * json 格式错误 缺少请求体
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Resp<Void> validExceptionHandler(HttpServletRequest request,MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> error = fieldErrors.stream().map(field -> field.getField() + ":" + field.getRejectedValue()).toList();
        log.error("全局异常处理器|MethodArgumentNotValidException|[{}] {} [ex] {}", request.getMethod(), getCurrentRequestUrl(), error);
        return Resp.buildFailure(CommonRespCode.FAIL.code(), String.valueOf(error));
    }
    /**
     * 其他所有 Exception
     */
    @ExceptionHandler(value = Exception.class)
    public Resp<Void> otherExceptionHandler(HttpServletRequest request, Throwable throwable) {
        log.error("全局异常处理器|Exception|[{}] {} ", request.getMethod(), getCurrentRequestUrl(), throwable);
        return Resp.buildFailure(INTERNAL_SERVER_ERROR.code(),INTERNAL_SERVER_ERROR.msg());
    }
    /**
     * 获取当前请求url
     */
    private String getCurrentRequestUrl() {
        RequestAttributes request = RequestContextHolder.getRequestAttributes();
        if (null == request) {
            return null;
        }
        ServletRequestAttributes servletRequest = (ServletRequestAttributes) request;
        return servletRequest.getRequest().getRequestURI();
    }
}
