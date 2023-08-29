package com.tomato.cloud.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * 自定义FeignRequestInterceptor,传递traceId
 *
 * @author lizhifu
 * @since 2023/8/2
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

	private final Tracer tracer;

	public FeignRequestInterceptor(Tracer tracer) {
		this.tracer = tracer;
	}

	@Override
	public void apply(RequestTemplate requestTemplate) {

		// 从MDC中获取当前的Trace ID，并添加到请求头中
		// 获取当前线程的 Context
		Context context = Context.current();
		// 设置当前线程的 MDC
		try (Scope ignored = context.makeCurrent()) {
			// String traceId = MDC.get("traceId");
			// if (traceId != null) {
			// log.info("需要传递traceId:{}", traceId);
			// requestTemplate.header("traceId", traceId);
			// }
		}
	}

}