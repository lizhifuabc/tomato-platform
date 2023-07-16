package com.tomato.web.core.config;

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
 * @since 2021/1/26
 */
@Configuration
@Slf4j
public class CorsFilterConfig {

    @Value("${access-control-allow-origin:*}")
    private String accessControlAllowOrigin;

    /**
     * 跨域配置
     * @return CorsFilter 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig());
        return new CorsFilter(source);
    }
    private CorsConfiguration corsConfig() {
        log.info("初始化 CorsFilter 跨域配置：{}",accessControlAllowOrigin);
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许向该服务器提交请求的URI，* 表示全部允许
        corsConfiguration.addAllowedOriginPattern(accessControlAllowOrigin);
        // 用于在CORS配置中暴露自定义头,Origin可以通过JS获取这个自定义响应头的值
        corsConfiguration.addExposedHeader("Authorization");
        // 允许的请求源,*表示允许所有来源
        corsConfiguration.addAllowedOrigin("*");
        // 允许的请求头,*表示允许所有的请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许的请求方法,*表示允许所有方法,如GET、POST等
        corsConfiguration.addAllowedMethod("*");
        // 允许跨域请求可以使用Cookie和其他认证信息
        corsConfiguration.setAllowCredentials(true);
        // 方法配置预检请求的有效期,单位为秒,在此期间不需要再次发送预检请求
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }
}