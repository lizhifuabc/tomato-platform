package com.tomato.web.handler;

import com.tomato.domain.resp.Resp;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
     * 其他所有 Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp otherExceptionHandler(HttpServletRequest req, Throwable throwable) {
        log.error("[otherExceptionHandler]", throwable);
        return Resp.buildFailure(INTERNAL_SERVER_ERROR.code(),INTERNAL_SERVER_ERROR.msg());
    }
}
