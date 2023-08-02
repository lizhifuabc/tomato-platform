package com.tomato.cloud.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Objects;

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
    public void apply(RequestTemplate template) {
        Span span = tracer.currentSpan();
        if (Objects.nonNull(span)) {
//            String traceId = span.context().traceId();
//            log.info("系统之间传递traceId:{}", traceId);
//            template.header("Trace-Id", traceId);
//            template.header("traceId", traceId);
//            MDC.put("traceId", traceId);
//            MDC.put("Trace-Id", traceId);
        }
    }
}
