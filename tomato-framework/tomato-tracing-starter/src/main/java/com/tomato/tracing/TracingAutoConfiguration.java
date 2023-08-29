package com.tomato.tracing;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * 自动配置
 *
 * @author lizhifu
 * @since 2023/5/2
 */
@AutoConfiguration
public class TracingAutoConfiguration {

	private static final Logger log = LoggerFactory.getLogger(TracingAutoConfiguration.class);

	@PostConstruct
	public void postConstruct() {
		log.info("tomato-tracing-starter 自动配置");
	}

}
