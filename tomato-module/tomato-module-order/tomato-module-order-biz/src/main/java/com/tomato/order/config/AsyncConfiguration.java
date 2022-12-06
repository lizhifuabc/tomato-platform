package com.tomato.order.config;

import com.tomato.dynamic.thread.executor.DynamicThreadPoolExecutor;
import com.tomato.dynamic.thread.thread.CustomThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 自定义线程池
 *
 * @author lizhifu
 * @date 2022/11/30
 */
@Configuration
@Slf4j
public class AsyncConfiguration implements AsyncConfigurer {
    public ThreadPoolTaskExecutor executor() {
        //Spring封装的一个线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(30);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("asyncEventTaskExecutor--orderSuccess-");
        executor.initialize();
        return executor;
    }
    @Bean("asyncTaskExecutor")
    public DynamicThreadPoolExecutor asyncTaskExecutor() {
        CustomThreadFactory customThreadFactory = new CustomThreadFactory("asyncTaskExecutor-test");
        // 自定义线程池
        DynamicThreadPoolExecutor executor = new DynamicThreadPoolExecutor(
                5,
                10,
                60,
                new LinkedBlockingQueue<>(),
                customThreadFactory
                );

        executor.setQueueTimeout(1L);
        executor.setRunTimeout(1L);
        return executor;
    }
    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }

    /**
     * 异常处理
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error(String.format("[async] task{} error:", method), ex);
    }
}
