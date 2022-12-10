package com.tomato.jackson.config;

import com.fasterxml.jackson.databind.*;
import com.tomato.jackson.module.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Jackson 配置
 * @author lizhifu
 */
@AutoConfiguration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfiguration {
	@Bean
	public ObjectMapper mapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}
