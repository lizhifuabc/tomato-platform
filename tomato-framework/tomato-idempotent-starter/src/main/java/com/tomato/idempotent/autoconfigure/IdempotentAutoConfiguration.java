package com.tomato.idempotent.autoconfigure;

import com.tomato.idempotent.aspect.IdempotentAspect;
import com.tomato.idempotent.properties.IdempotentProperties;
import com.tomato.idempotent.strategy.IdempotentStrategy;
import com.tomato.idempotent.strategy.IdempotentStrategyFactory;
import com.tomato.idempotent.strategy.impl.RedisStrategyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 自动配置
 *
 * @author lizhifu
 */
@AutoConfiguration
@EnableConfigurationProperties(IdempotentProperties.class)
public class IdempotentAutoConfiguration implements InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(IdempotentAutoConfiguration.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("tomato-idempotent-starter 自动装配");
	}

	@Bean
	public IdempotentAspect idempotentAspect(IdempotentStrategyFactory idempotentStrategyFactory) {
		return new IdempotentAspect(idempotentStrategyFactory);
	}

	@Bean
	public IdempotentStrategyFactory idempotentStrategyFactory(ApplicationContext applicationContext) {
		return new IdempotentStrategyFactory(applicationContext);
	}

	@Bean
	public IdempotentStrategy idempotentStrategy(StringRedisTemplate redisTemplate) {
		return new RedisStrategyImpl(redisTemplate);
	}

}
