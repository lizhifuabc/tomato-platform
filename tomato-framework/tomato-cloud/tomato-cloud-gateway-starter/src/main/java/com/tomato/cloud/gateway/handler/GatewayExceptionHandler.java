package com.tomato.cloud.gateway.handler;

import com.tomato.cloud.gateway.utils.WebFluxUtils;
import com.tomato.common.domain.resp.Resp;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 网关统一异常处理 {@link ResponseEntityExceptionHandler} {@link ExceptionHandlingWebHandler}
 * 可以查看异常排序情况
 *
 * @author lizhifu
 * @since 2023/7/30
 */
@Slf4j
public class GatewayExceptionHandler implements WebExceptionHandler {

	private final Tracer tracer;

	public GatewayExceptionHandler(Tracer tracer) {
		this.tracer = tracer;
	}

	@Override
	public @NotNull Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();
		if (exchange.getResponse().isCommitted()) {
			return Mono.error(ex);
		}
		String msg;
		if (ex instanceof NotFoundException) {
			msg = "服务未找到";
		}
		else if (ex instanceof ResponseStatusException responseStatusException) {
			msg = responseStatusException.getMessage();
		}
		else {
			msg = "内部服务器错误";
		}
		log.error("[网关异常处理]请求路径:{},异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());
		Resp<String> resp = Resp.buildFailure(msg);
		Span span = tracer.currentSpan();
		if (Objects.nonNull(span)) {
			resp.setTraceId(span.context().traceId());
		}
		return WebFluxUtils.writeJsonResponse(response, resp);
	}

}
