package com.tomato.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

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
    @Order(Integer.MIN_VALUE)
    public CorsFilter defaultCorsFilter() {
        log.info("初始化 CorsFilter 跨域配置：{}",accessControlAllowOrigin);
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许向该服务器提交请求的URI，* 表示全部允许
        configuration.addAllowedOriginPattern(accessControlAllowOrigin);
        configuration.setAllowedOrigins(Arrays.asList("*"));
        // 允许提交请求的方法，*表示全部允许
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "DELETE", "OPTION"));
        // 允许访问的头信息,*表示全部
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}