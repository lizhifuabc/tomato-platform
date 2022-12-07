package com.tomato.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 * @author lizhifu
 */
@Configuration
@Slf4j
public class CorsFilterConfig {

    @Value("${access-control-allow-origin:*}")
    private String accessControlAllowOrigin;
    
    /**
     * 跨域配置
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter () {
        log.info("初始化 CorsFilter 跨域配置：{}",accessControlAllowOrigin);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern(accessControlAllowOrigin);
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}