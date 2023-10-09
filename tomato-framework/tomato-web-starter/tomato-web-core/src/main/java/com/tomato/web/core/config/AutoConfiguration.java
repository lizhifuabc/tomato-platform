package com.tomato.web.core.config;

import com.tomato.web.core.aop.ControllerParamJoinPoint;
import com.tomato.web.core.constants.WebConstants;
import com.tomato.web.core.properties.CustomWebProperties;
import com.tomato.web.core.xss.XssHttpServletFilter;
import com.tomato.web.core.xss.XssUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.owasp.validator.html.PolicyException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;

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
		log.info("tomato-web-starter ControllerParamJoinPoint 自动装配");
		return new ControllerParamJoinPoint();
	}

	@Bean
	@ConditionalOnProperty(prefix = WebConstants.PROPERTY_PREFIX_WEB, name = "xss-filter-enable",
			havingValue = "true")
	XssHttpServletFilter xssHttpServletFilter(XssUtil xssUtil) {
		log.info("tomato-web-starter XssHttpServletFilter 自动装配");
		return new XssHttpServletFilter(xssUtil);
	}

	@Bean
	@ConditionalOnBean
	XssUtil xssUtil() throws PolicyException, FileNotFoundException {
		log.info("tomato-web-starter XssUtil 自动装配");
		return new XssUtil();
	}
}
