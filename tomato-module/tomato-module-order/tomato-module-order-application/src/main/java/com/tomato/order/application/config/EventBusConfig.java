package com.tomato.order.application.config;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * EventBusConfig
 *
 * @author lizhifu
 * @since 2023/8/19
 */
@Configuration
public class EventBusConfig {
    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(1024);
        taskExecutor.setKeepAliveSeconds(300);
        return taskExecutor;
    }
    @Bean
    public AsyncEventBus asyncEventBus(){
        return new AsyncEventBus(taskExecutor());
    }
}
