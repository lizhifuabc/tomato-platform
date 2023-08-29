package com.tomato.idempotent.strategy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 策略工厂
 *
 * @author lizhifu
 * @since 2023/4/11
 */
@Component
public class IdempotentStrategyFactory implements InitializingBean {

	private final ApplicationContext applicationContext;

	private final HashMap<String, IdempotentStrategy> strategyMap = new HashMap<>();

	public IdempotentStrategyFactory(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public IdempotentStrategy getStrategy(String name) {
		return strategyMap.get(name);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		applicationContext.getBeansOfType(IdempotentStrategy.class).forEach((k, v) -> {
			strategyMap.put(v.name(), v);
		});
	}

}