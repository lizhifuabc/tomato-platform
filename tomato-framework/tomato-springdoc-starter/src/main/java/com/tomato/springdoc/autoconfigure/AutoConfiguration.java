package com.tomato.springdoc.autoconfigure;

import com.tomato.springdoc.config.SpringDocConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 自动配置
 * @author lizhifu
 */
@Configuration(proxyBeanMethods = false)
@Import(value = {
        SpringDocConfig.class
})
public class AutoConfiguration implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(AutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("tomato-springdoc-starter 自动装配");
    }
}
