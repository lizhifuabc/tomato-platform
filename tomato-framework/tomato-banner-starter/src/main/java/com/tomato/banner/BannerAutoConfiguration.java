package com.tomato.banner;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * banner 自动配置
 *
 * @author lizhifu
 * @since 2023/7/14
 */
@Slf4j
@AutoConfiguration
public class BannerAutoConfiguration {
    @PostConstruct
    public void postConstruct() {
        log.info("tomato-banner-starter 自动装配");
    }
    @Bean
    public BannerApplicationRunner bannerApplicationRunner() {
        return new BannerApplicationRunner();
    }
}
