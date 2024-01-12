package com.tomato.cloud.feign.config;

import com.tomato.common.constants.HttpHeadersConstants;
import com.tomato.common.holder.TenantContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

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

		HttpServletRequest httpServletRequest = getHttpServletRequest();
		if (httpServletRequest != null) {
			Long tenantId = TenantContextHolder.getTenantId();
			log.info("需要传递tenantId:{}", tenantId);
			Optional.ofNullable(tenantId)
					.ifPresent(x -> requestTemplate.header(HttpHeadersConstants.X_TOMATO_TENANT_ID, String.valueOf(x)));
		}
	}

	private HttpServletRequest getHttpServletRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return Optional.ofNullable(requestAttributes)
				.map(x -> ((ServletRequestAttributes) x).getRequest())
				.orElse(null);
	}
}