package com.tomato.rabbitmq.monitor;

import com.tomato.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * RabbitMQ 线程池监控
 *
 * @author lizhifu
 * @since 2022/12/26
 */
@Slf4j
public class RabbitThreadPoolMonitor implements InitializingBean {
    private static final String FILED_NAME = "executorService";
    private final Map<String, AbstractConnectionFactory> abstractConnectionFactoryMap;
    private final Map<String, ThreadPoolExecutor> rabbitmqThreadPoolTaskExecutor = new HashMap<>();
    public RabbitThreadPoolMonitor(Map<String, AbstractConnectionFactory> abstractConnectionFactoryMap) {
        this.abstractConnectionFactoryMap = abstractConnectionFactoryMap;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        abstractConnectionFactoryMap.forEach((beanName, abstractConnectionFactor) -> {
            if(abstractConnectionFactor instanceof CachingConnectionFactory){

            }
            // TODO 线程池监控
            log.info("RabbitThreadPoolMonitor beanName :{},AbstractConnectionFactory:{}",beanName,abstractConnectionFactor);
            ExecutorService executor = (ExecutorService) ReflectUtil.getFieldValue(abstractConnectionFactor, FILED_NAME);
            if (Objects.nonNull(executor)) {
                if (executor instanceof ThreadPoolExecutor) {
                    ThreadPoolExecutor threadPoolTaskExecutor = (ThreadPoolExecutor) executor;
                    rabbitmqThreadPoolTaskExecutor.put(beanName, threadPoolTaskExecutor);
                    log.info("Rabbitmq executor name {}", beanName);
                } else {
                    log.warn("Custom thread pools only support ThreadPoolExecutor");
                }
            }
        });
    }
}
