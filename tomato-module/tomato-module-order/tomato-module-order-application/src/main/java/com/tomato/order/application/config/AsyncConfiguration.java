package com.tomato.order.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
    /**
     * 默认不定长线程池
     */
    private static final ThreadPoolExecutor COMMON_POOL = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    /**
     * 自定义线程池
     * @return ThreadPoolTaskExecutor
     */
    public ThreadPoolTaskExecutor executor() {
        // 不定长线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(0);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.setThreadNamePrefix("order-async-exe-");
        executor.initialize();
        return executor;
    }
    @Override
    public Executor getAsyncExecutor() {
        return COMMON_POOL;
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
