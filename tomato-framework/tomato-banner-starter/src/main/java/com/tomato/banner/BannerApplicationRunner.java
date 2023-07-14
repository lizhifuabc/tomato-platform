package com.tomato.banner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * banner 配置
 *
 * @author lizhifu
 * @since 2023/7/14
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("tomato-banner-starter 自动装配");
    }
}
