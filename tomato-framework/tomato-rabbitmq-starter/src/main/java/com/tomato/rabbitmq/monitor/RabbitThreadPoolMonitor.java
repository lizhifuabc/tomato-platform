package com.tomato.rabbitmq.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * RabbitMQ 线程池监控
 *
 * @author lizhifu
 * @since 2022/12/26
 */
@Slf4j
public class RabbitThreadPoolMonitor implements InitializingBean {
    private final Map<String, AbstractConnectionFactory> abstractConnectionFactoryMap;
    private final Map<String, ThreadPoolExecutor> rabbitmqThreadPoolTaskExecutor = new HashMap<>();
    public RabbitThreadPoolMonitor(Map<String, AbstractConnectionFactory> abstractConnectionFactoryMap) {
        this.abstractConnectionFactoryMap = abstractConnectionFactoryMap;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        abstractConnectionFactoryMap.forEach((beanName, abstractConnectionFactor) -> {
            log.info("RabbitThreadPoolMonitor beanName :{},AbstractConnectionFactory:{}",beanName,abstractConnectionFactor);
        });
    }
}
