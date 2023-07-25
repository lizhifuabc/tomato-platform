package com.tomato.jackson.config;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.*;
import com.tomato.jackson.utils.JacksonUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


/**
 * Jackson 配置
 * @author lizhifu
 */
@AutoConfiguration
public class JacksonConfiguration {
	private static final Logger log = LoggerFactory.getLogger(JacksonConfiguration.class);
	@PostConstruct
	public void postConstruct() {
		log.info("tomato-jackson-starter 自动装配");
	}

	/**
	 * 2.15 中显着的更改是引入了处理限制，以防止恶意输入导致的内存问题。
	 * @return Jackson2ObjectMapperBuilderCustomizer
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customStreamReadConstraints() {
		return (builder) -> builder.postConfigurer((objectMapper) -> objectMapper.getFactory()
				.setStreamReadConstraints(StreamReadConstraints.builder().maxNestingDepth(2000).build()));
	}
	/**
	 * 转换器全局配置
	 * @return MappingJackson2HttpMessageConverter
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		log.info("tomato-jackson-starter mappingJackson2HttpMessageConverter 自动装配");
		return new MappingJackson2HttpMessageConverter(objectMapper);
	}
	@Bean
	public CustomizerJackson2ObjectMapperBuilderCustomizer customizerJackson2ObjectMapperBuilderCustomizer() {
		return new CustomizerJackson2ObjectMapperBuilderCustomizer();
	}

	/**
	 * jackson工具类
	 * @return JacksonUtils
	 */
	@Bean
	public JacksonUtils jacksonUtils(ObjectMapper objectMapper) {
		log.info("tomato-jackson-starter jacksonUtils 自动装配");
		return new JacksonUtils(objectMapper);
	}
}
