package com.tomato.jackson.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tomato.jackson.module.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import static com.tomato.jackson.module.JavaTimeModule.NORM_DATETIME_PATTERN;

/**
 * Jackson 配置
 * @author lizhifu
 */
@AutoConfiguration
@ConditionalOnClass(ObjectMapper.class)//当classpath下发现该类的情况下进行自动配置
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfiguration {
	@Bean
	@ConditionalOnMissingBean // 当容器里没有指定Bean的情况下创建该对象
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		return builder -> {
			// 设置时区
			builder.locale(Locale.CHINA);
			builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
			// 设置日期格式
			builder.simpleDateFormat(NORM_DATETIME_PATTERN);
			// 设置序列化
			builder.serializerByType(Long.class, ToStringSerializer.instance);
			// 设置反序列化
			builder.modules(new JavaTimeModule());
		};
	}
}
