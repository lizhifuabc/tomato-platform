package com.tomato.order.infrastructure.config;

import com.tomato.order.infrastructure.holder.ApplicationContextHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ApplicationBaseAutoConfiguration
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Configuration
public class ApplicationBaseAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }
}
