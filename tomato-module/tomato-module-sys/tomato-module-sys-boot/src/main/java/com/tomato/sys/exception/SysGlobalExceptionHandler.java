package com.tomato.sys.exception;

import com.tomato.common.resp.Resp;
import com.tomato.web.core.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.*;

/**
 * 全局异常处理器
 *
 * @see GlobalExceptionHandler
 * @author lizhifu
 * @since 2023/6/9
 */
@Slf4j
@RestControllerAdvice
public class SysGlobalExceptionHandler {

	@ExceptionHandler(value = AccessDeniedException.class)
	@ResponseBody
	public Resp<Void> handleAccessDeniedException(AccessDeniedException e) {
		log.info("没有权限:{}", e.getLocalizedMessage());
		return Resp.buildFailure(String.valueOf(UNAUTHORIZED.value()), e.getLocalizedMessage());
	}

	@ExceptionHandler(value = AuthenticationException.class)
	@ResponseBody
	public Resp<Void> handleAuthenticationException(AuthenticationException e) {
		log.info("登录失败:{}", e.getLocalizedMessage());
		return Resp.buildFailure(String.valueOf(UNAUTHORIZED.value()), e.getLocalizedMessage());
	}

}
