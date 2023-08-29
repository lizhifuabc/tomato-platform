package com.tomato.notice.service.config;

import com.tomato.notice.service.thread.CustomThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * webClient线程池配置
 *
 * @author lizhifu
 * @since 2023/5/15
 */
@Configuration
@Slf4j
public class ThreadPoolConfig {

	private static final int CORE_POOL_SIZE = 10 * Runtime.getRuntime().availableProcessors();

	private static final int QUEUE_SIZE = 100000;

	private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2;

	private static final int KEEP_ALIVE_TIME = 60;

	@Bean
	public CustomThreadPoolExecutor customThreadPoolExecutor() {
		log.info("初始化线程池,corePoolSize:{},maxPoolSize:{},queueSize:{}", CORE_POOL_SIZE, MAX_POOL_SIZE, QUEUE_SIZE);
		CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
				KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE),
				new ThreadPoolExecutor.CallerRunsPolicy());
		return customThreadPoolExecutor;
	}

}
