package com.tomato.account.config;

import com.tomato.dynamic.thread.executor.DynamicThreadPoolExecutor;
import com.tomato.dynamic.thread.thread.CustomThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 自定义线程池
 *
 * @author lizhifu
 * @date 2022/11/30
 */
@Configuration
@Slf4j
public class AsyncConfiguration implements AsyncConfigurer {
    @Bean("asyncTaskExecutorAccount")
    public DynamicThreadPoolExecutor asyncTaskExecutorAccount() {
        CustomThreadFactory customThreadFactory = new CustomThreadFactory("asyncTaskExecutor-account");
        // 自定义线程池
        DynamicThreadPoolExecutor executor = new DynamicThreadPoolExecutor(
                2,
                5,
                60,
                new LinkedBlockingQueue<>(),
                customThreadFactory
                );
        executor.setQueueTimeout(3000L);
        executor.setRunTimeout(3000L);
        return executor;
    }
    @Override
    public Executor getAsyncExecutor() {
        return asyncTaskExecutorAccount();
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
