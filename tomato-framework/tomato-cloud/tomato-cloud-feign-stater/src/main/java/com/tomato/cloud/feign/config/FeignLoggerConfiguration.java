package com.tomato.cloud.feign.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 开启日志
 *
 * @author lizhifu
 * @since 2023/6/6
 */
@Configuration
public class FeignLoggerConfiguration {
    /**
     * 全局配置日志
     * <p>
     * 1. NONE（默认） --- 不记录任何日志
     * 2. BASIC ---	仅记录请求方法，URL，响应状态代码以及执行时间（适合生产环境）
     * 3. HEADERS --- 记录BASIC级别的基础上，记录请求和响应的header
     * 4. FULL --- 记录请求和响应header，body和元数据
     *
     * @return Logger.Level 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    /**
     * 设置连接超时时间
     * @return Request.Options 超时配置
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
    }
    /**
     * 重试次数
     * @return Retryer 重试器
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}
