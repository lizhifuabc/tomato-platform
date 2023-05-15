package com.tomato.notice.service.config;

import com.tomato.notice.service.thread.CustomThreadPoolExecutor;
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
public class WebClientThreadPoolConfig {
    private static final int CORE_POOL_SIZE = (Integer) Optional.ofNullable(System.getProperty("reactor.schedulers.defaultBoundedElasticSize")).map(Integer::parseInt).orElseGet(() -> {
        return 10 * Runtime.getRuntime().availableProcessors();
    });
    private static final int QUEUE_SIZE = (Integer)Optional.ofNullable(System.getProperty("reactor.schedulers.defaultBoundedElasticQueueSize")).map(Integer::parseInt).orElse(100000);
    private static final int MAX_POOL_SIZE = (Integer)Optional.ofNullable(System.getProperty("reactor.schedulers.defaultBoundedElasticSize")).map(Integer::parseInt).orElseGet(() -> {
        return 10 * Runtime.getRuntime().availableProcessors();
    });
    private static final int KEEP_ALIVE_TIME = 60;
    @Bean
    public CustomThreadPoolExecutor customThreadPoolExecutor() {
        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_SIZE),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return customThreadPoolExecutor;
    }
}
