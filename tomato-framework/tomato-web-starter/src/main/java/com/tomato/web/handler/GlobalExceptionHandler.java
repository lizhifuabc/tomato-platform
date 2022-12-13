package com.tomato.web.handler;

import com.tomato.domain.resp.Resp;
import com.tomato.domain.resp.code.CommonRespCode;
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

import java.util.List;
import java.util.stream.Collectors;

import static com.tomato.domain.resp.code.CommonRespCode.INTERNAL_SERVER_ERROR;

/**
 * 全局异常处理器
 *
 * @author lizhifu
 * @date 2022/12/7
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * json 格式错误 缺少请求体
     */
    @ResponseBody
    @ExceptionHandler({TypeMismatchException.class, BindException.class})
    public Resp paramExceptionHandler(Exception e) {
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
    public Resp otherExceptionHandler(HttpServletRequest req, Throwable throwable) {
        log.error("[otherExceptionHandler]", throwable);
        return Resp.buildFailure(INTERNAL_SERVER_ERROR.code(),INTERNAL_SERVER_ERROR.msg());
    }
}
