package com.tomato.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomato.web.aop.ControllerParamJoinPoint;
import com.tomato.web.constants.WebConstants;
import com.tomato.web.properties.CustomWebProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2023/4/8
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CustomWebProperties.class)
@Slf4j
public class AutoConfiguration {
    @PostConstruct
    public void postConstruct() {
        log.info("tomato-web-starter Auto Configure.");
    }
    @Bean
    @ConditionalOnProperty(prefix = WebConstants.PROPERTY_PREFIX_WEB, name = "controller-point-enable", havingValue = "true")
    ControllerParamJoinPoint controllerParamJoinPoint(ObjectMapper objectMapper){
        log.info("tomato-web-starter ControllerParamJoinPoint Auto Configure.");
        return new ControllerParamJoinPoint(objectMapper);
    }
}
