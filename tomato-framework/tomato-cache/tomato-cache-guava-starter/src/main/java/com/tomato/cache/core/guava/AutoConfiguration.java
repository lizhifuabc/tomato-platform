package com.tomato.cache.core.guava;

import com.tomato.cache.core.guava.impl.GuavaLocalCacheService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2023/5/2
 */
@Configuration(proxyBeanMethods = false)
public class AutoConfiguration {

	private static final Logger log = LoggerFactory.getLogger(AutoConfiguration.class);

	@PostConstruct
	public void postConstruct() {
		log.info("guava 自动配置");
	}

	@Bean
	@ConditionalOnProperty(name = "cache.type.local", havingValue = "guava")
	public GuavaLocalCacheService guavaLocalCacheService() {
		return new GuavaLocalCacheService();
	}

}
