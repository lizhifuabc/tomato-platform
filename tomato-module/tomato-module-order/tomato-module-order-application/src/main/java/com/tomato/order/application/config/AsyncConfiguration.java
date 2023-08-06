package com.tomato.order.application.config;

import com.tomato.tracing.thread.TraceIdThreadPoolTaskExecutor;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.*;

/**
 * 自定义线程池
 *
 * @author lizhifu
 * @since  2022/11/30
 */
@Configuration
@Slf4j
public class AsyncConfiguration implements AsyncConfigurer {
    @Resource
    private MeterRegistry meterRegistry;
    @Override
    @Bean(name = "orderAsyncExecutor")
    public Executor getAsyncExecutor() {
        log.info("初始化自定义线程池");
        // 不定长线程池
        return new TraceIdThreadPoolTaskExecutor(meterRegistry);
    }

    /**
     * 异常处理
     * @return AsyncUncaughtExceptionHandler 异常处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error(String.format("[async] task{} error:", method), ex);
    }
}
