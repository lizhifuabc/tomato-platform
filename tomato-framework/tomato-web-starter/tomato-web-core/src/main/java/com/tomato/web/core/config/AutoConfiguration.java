package com.tomato.web.core.config;

import com.tomato.web.core.aop.ControllerParamJoinPoint;
import com.tomato.web.core.constants.WebConstants;
import com.tomato.web.core.properties.CustomWebProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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
		log.info("tomato-web-starter 自动装配");
	}

	@Bean
	@ConditionalOnProperty(prefix = WebConstants.PROPERTY_PREFIX_WEB, name = "controller-point-enable",
			havingValue = "true")
	ControllerParamJoinPoint controllerParamJoinPoint() {
		log.info("tomato-web-starter ControllerParamJoinPoint Auto Configure.");
		return new ControllerParamJoinPoint();
	}

}
