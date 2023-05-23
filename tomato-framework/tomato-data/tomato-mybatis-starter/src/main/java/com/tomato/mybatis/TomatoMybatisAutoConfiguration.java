package com.tomato.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2023/5/23
 */
@EnableConfigurationProperties(TomatoMybatisProperties.class)
@AutoConfiguration
@Slf4j
public class TomatoMybatisAutoConfiguration implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
      log.info("tomato-mybatis-starter 自动装配");
    }
}
