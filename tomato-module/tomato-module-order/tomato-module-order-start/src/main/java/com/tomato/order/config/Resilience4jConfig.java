package com.tomato.order.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Resilience4jConfig 配置
 *
 * @author lizhifu
 * @since 2023/9/11
 */
@Configuration
@Slf4j
public class Resilience4jConfig {

    /**
     * 监听 Resilience4j 断路器（CircuitBreaker）的注册事件
     * @return RegistryEventConsumer<CircuitBreaker> 监听 Resilience4j 断路器（CircuitBreaker）的注册事件
     */
    @Bean
    public RegistryEventConsumer<CircuitBreaker> myRegistryEventConsumer() {

        return new RegistryEventConsumer<CircuitBreaker>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> {
                  log.info("新的断路器被添加:{}" , event.toString());
                });
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
                log.info("新的断路器被删除:{}", entryRemoveEvent.getRemovedEntry().getName());
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
                log.info("新的断路器被替换:{}" , entryReplacedEvent.getNewEntry().getName());
            }
        };
    }

    /**
     * 监听 Resilience4j 重试（Retry）的注册事件
     * @return RegistryEventConsumer<Retry> 监听 Resilience4j 重试（Retry）的注册事件
     */
    @Bean
    public RegistryEventConsumer<Retry> myRetryRegistryEventConsumer() {

        return new RegistryEventConsumer<Retry>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<Retry> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event ->{
                    log.info("新的重试机制被添加:{}" , event.toString());
                });
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<Retry> entryRemoveEvent) {
                log.info("新的重试机制被删除:{}", entryRemoveEvent.getRemovedEntry().getName());

            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<Retry> entryReplacedEvent) {
                log.info("新的重试机制被替换:{}" , entryReplacedEvent.getNewEntry().getName());
            }
        };
    }
}
