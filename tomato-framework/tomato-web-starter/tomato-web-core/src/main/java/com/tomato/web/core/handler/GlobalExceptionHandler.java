package com.tomato.web.core.handler;

import com.tomato.common.exception.BusinessException;
import com.tomato.common.resp.Resp;
import com.tomato.common.constants.CommonRespCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;

import static com.tomato.common.constants.CommonRespCode.INTERNAL_SERVER_ERROR;

/**
 * 全局异常处理器
 *
 * @author lizhifu
 * @since  2022/12/7
 */
@Slf4j
@RestControllerAdvice // 全局拦截异常注解 @RestControllerAdvice = @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {
    /**
     * 业务异常处理
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Resp<Void> businessExceptionHandler(BusinessException e) {
        log.error("全局业务异常,URL:{},异常信息:{}", getCurrentRequestUrl(), e.getMessage());
        return Resp.buildFailure(CommonRespCode.INTERNAL_SERVER_ERROR.code(), e.getMessage());
    }
    /**
     * 唯一性索引异常
     */
//    @ResponseBody
//    @ExceptionHandler(DuplicateKeyException.class)
//    public Resp<Void> duplicateKeyException(DuplicateKeyException e) {
//        log.error("唯一性索引异常,URL:{}", getCurrentRequestUrl(), e);
//        return Resp.buildFailure(CommonRespCode.DUPLICATE_KEY_SERVER_ERROR.code(), CommonRespCode.DUPLICATE_KEY_SERVER_ERROR.msg());
//    }
    /**
     * json 格式错误 缺少请求体
     */
    @ResponseBody
    @ExceptionHandler({TypeMismatchException.class, BindException.class})
    public Resp<Void> paramExceptionHandler(Exception e) {
        if (e instanceof BindException) {
            if (e instanceof MethodArgumentNotValidException) {
                List<FieldError> fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
                List<String> msgList = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
                return Resp.buildFailure(CommonRespCode.FAIL.code(), String.join(",", msgList));
            }

            List<FieldError> fieldErrors = ((BindException) e).getFieldErrors();
            List<String> error = fieldErrors.stream().map(field -> field.getField() + ":" + field.getRejectedValue()).collect(Collectors.toList());
            String errorMsg = CommonRespCode.FAIL.msg() + ":" + error;
            return Resp.buildFailure(CommonRespCode.FAIL.code(), errorMsg);
        }
        return Resp.buildFailure(CommonRespCode.FAIL.code(),CommonRespCode.FAIL.msg());
    }
    /**
     * 其他所有 Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp<Void> otherExceptionHandler(HttpServletRequest req, Throwable throwable) {
        log.error("[otherExceptionHandler]", throwable);
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
