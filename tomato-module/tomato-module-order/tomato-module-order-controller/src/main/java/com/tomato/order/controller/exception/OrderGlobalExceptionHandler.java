package com.tomato.order.controller.exception;

import com.tomato.common.resp.Resp;
import com.tomato.web.core.handler.GlobalExceptionHandler;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * OrderGlobalExceptionHandler
 *
 * @see GlobalExceptionHandler
 * @author lizhifu
 * @since 2023/8/2
 */
@Slf4j
@RestControllerAdvice
public class OrderGlobalExceptionHandler {

	@ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
	@ResponseBody
	public Resp<Void> handleAccessDeniedException(SQLIntegrityConstraintViolationException e) {
		log.warn("数据重复:{}", e.getLocalizedMessage());
		return Resp.buildFailure(String.valueOf(HttpStatus.ACCEPTED.value()), e.getLocalizedMessage());
	}

	/**
	 * 远程调用异常
	 * 如果此处配置了，那么就不会走到 {@link org.springframework.cloud.openfeign.FallbackFactory} 中
	 * @param e 异常
	 * @return Resp
	 */
	@ExceptionHandler(value = FeignException.class)
	@ResponseBody
	public Resp<Void> feignException(FeignException e) {
		log.error("远程调用异常:{}", e.getLocalizedMessage());
		return Resp.buildFailure(String.valueOf(HttpStatus.ACCEPTED.value()), e.getLocalizedMessage());
	}

}
