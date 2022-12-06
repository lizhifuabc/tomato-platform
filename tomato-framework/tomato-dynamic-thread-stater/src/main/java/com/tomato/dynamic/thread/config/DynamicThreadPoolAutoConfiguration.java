package com.tomato.dynamic.thread.config;

import com.tomato.dynamic.thread.executor.DynamicThreadPoolExecutor;
import com.tomato.dynamic.thread.thread.CustomThreadFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 动态线程池自动装配
 *
 * @author lizhifu
 * @date 2022/12/4
 */
@AutoConfiguration
@EnableConfigurationProperties(DynamicThreadPoolProperties.class)
public class DynamicThreadPoolAutoConfiguration {
    /**
     * 内部监控线程池
     * @return
     */
    @Bean("asyncNotifyTaskExecutor")
    public DynamicThreadPoolExecutor asyncNotifyTaskExecutor() {
        CustomThreadFactory customThreadFactory = new CustomThreadFactory("asyncNotifyTaskExecutor");
        return new DynamicThreadPoolExecutor(
                2,
                4,
                60,
                new LinkedBlockingQueue(4096),
                customThreadFactory
        );
    }
}
