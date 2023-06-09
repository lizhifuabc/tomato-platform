package com.tomato.sys.exception;

import com.tomato.common.resp.Resp;
import com.tomato.web.core.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.*;

/**
 * 全局异常处理器
 * @see GlobalExceptionHandler
 * @author lizhifu
 * @since 2023/6/9
 */
@Slf4j
@RestControllerAdvice
public class SysGlobalExceptionHandler {
    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseBody
    public Resp<Void> handleBadCredentialsException(BadCredentialsException e) {
        log.info("登录失败:{}",e.getLocalizedMessage());
        return Resp.buildFailure(String.valueOf(UNAUTHORIZED.value()),e.getLocalizedMessage());
    }
}
