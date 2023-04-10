package com.tomato.springdoc.config;

import com.tomato.springdoc.properties.SpringDocProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * spring doc 配置
 *
 * @author lizhifu
 * @since 2023/4/10
 */
@Configuration
@EnableConfigurationProperties(SpringDocProperties.class)
@ConditionalOnProperty(
        name = {"springdoc.api-docs.enabled"},
        matchIfMissing = true
)
public class SpringDocConfig {

}
