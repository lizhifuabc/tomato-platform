package com.tomato.dynamic.thread.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 动态线程池自动装配
 *
 * @author lizhifu
 * @date 2022/12/4
 */
@AutoConfiguration
@EnableConfigurationProperties(DynamicThreadPoolProperties.class)
public class DynamicThreadPoolAutoConfiguration {

}
