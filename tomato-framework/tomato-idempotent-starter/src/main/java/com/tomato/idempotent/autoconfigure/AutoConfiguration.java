package com.tomato.idempotent.autoconfigure;

import com.tomato.idempotent.aspect.IdempotentAspect;
import com.tomato.idempotent.properties.IdempotentProperties;
import com.tomato.idempotent.strategy.IdempotentStrategyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 * @author lizhifu
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(IdempotentProperties.class)
public class AutoConfiguration implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(AutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("tomato-idempotent-starter Auto Configure.");
    }
    @Bean
    public IdempotentAspect idempotentAspect(IdempotentStrategyFactory idempotentStrategyFactory){
        return new IdempotentAspect(idempotentStrategyFactory);
    }
    @Bean
    public IdempotentStrategyFactory idempotentStrategyFactory(ApplicationContext applicationContext){
        return new IdempotentStrategyFactory(applicationContext);
    }
}
