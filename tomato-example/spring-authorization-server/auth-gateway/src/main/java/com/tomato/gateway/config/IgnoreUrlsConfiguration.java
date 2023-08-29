package com.tomato.gateway.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 忽略url配置
 *
 * @author lizhifu
 * @since 2023/4/5
 */
@Configuration
@ConditionalOnProperty(prefix = "secure.ignore", name = "urls", matchIfMissing = true)
@EnableConfigurationProperties(IgnoreUrlsConfiguration.IgnoreUrlsConfig.class)
public class IgnoreUrlsConfiguration {

	@Data
	@ConfigurationProperties(prefix = "secure.ignore")
	public static class IgnoreUrlsConfig {

		private String[] urls = new String[] { "0" };

	}

}