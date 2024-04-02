package com.tomato.dynamic.db.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 动态数据源配置
 *
 * @author lizhifu
 * @since 2024/4/1
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ImportAutoConfiguration({ DynamicDataSourceConfig.class })
@EnableConfigurationProperties({ DynamicDataSourceProperties.class })
@AutoConfigureBefore(value = DataSourceAutoConfiguration.class)
@ConditionalOnProperty(prefix = DynamicDataSourceProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class DynamicDataSourceAutoConfiguration {

}
