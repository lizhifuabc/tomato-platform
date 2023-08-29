package com.tomato.notice.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * spring 线程池配置
 *
 * @author lizhifu
 * @since 2023/5/15
 */
@Configuration
@Slf4j
public class AsyncConfiguration implements AsyncConfigurer {

	@Bean(name = "asyncTaskExecutor")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(10);
		executor.setThreadNamePrefix("tomato-async-task-");
		executor.setKeepAliveSeconds(60);
		return executor;
	}

	@Override
	public TaskExecutor getAsyncExecutor() {
		return taskExecutor();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> log.error(String.format("[async] task{} error:", method), ex);
	}

}
