package com.tomato.springdoc.autoconfigure;

import com.tomato.springdoc.config.SpringDocConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 自动配置
 * @author lizhifu
 */
@AutoConfiguration
@Import(value = {SpringDocConfig.class})
public class SpringDocAutoConfiguration implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(SpringDocAutoConfiguration.class);
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("tomato-springdoc-starter 自动装配");
    }
}
