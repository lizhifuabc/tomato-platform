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
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // 允许向该服务器提交请求的URI，*表示全部允许
        config.addAllowedOriginPattern(accessControlAllowOrigin);
        // 允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        // 默认 1800L
        config.setMaxAge(1800L);
        // 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}