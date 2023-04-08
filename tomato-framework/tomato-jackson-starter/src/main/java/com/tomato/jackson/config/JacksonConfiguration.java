package com.tomato.jackson.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tomato.jackson.utils.JacksonUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Jackson 配置
 * @author lizhifu
 */
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfiguration {
	private static final Logger log = LoggerFactory.getLogger(JacksonConfiguration.class);
	@PostConstruct
	public void postConstruct() {
		log.info("tomato-jackson-starter Auto Configure.");
	}

	/**
	 * 如果没有手动定义ObjectMapper类型的Bean，
	 * 那么它会自动创建一个名为"jacksonObjectMapper"的默认Bean，并注册到Spring容器中
	 * @return ObjectMapper
	 */
	@Bean(name = "jacksonObjectMapper")
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 设置为中国上海时区
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		// 设置为中国
		objectMapper.setLocale(Locale.CHINA);
		// 序列化时，日期的统一格式
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 排序key
		objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		// 忽略空bean转json错误
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 忽略在json字符串中存在，在java类中不存在字段，防止错误。
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 单引号处理
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);

		// 序列换成json时,将所有的long变成string,js中long过长精度丢失
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		// TODO xss 处理
		// simpleModule.addDeserializer(String.class, new XssStringJsonDeserializer());

		objectMapper.registerModule(simpleModule);
		objectMapper.registerModule(new Jdk8Module());
		objectMapper.registerModule(new JavaTimeModule());

		log.info("tomato-jackson-starter objectMapper Auto Configure.");
		return objectMapper;
	}
	/**
	 * 转换器全局配置
	 * @return MappingJackson2HttpMessageConverter
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		log.info("tomato-jackson-starter mappingJackson2HttpMessageConverter Auto Configure.");
		return new MappingJackson2HttpMessageConverter(objectMapper);
	}
	/**
	 * jackson工具类
	 * @return JacksonUtils
	 */
	@Bean
	public JacksonUtils jacksonUtils(ObjectMapper objectMapper) {
		log.info("tomato-jackson-starter jacksonUtils Auto Configure.");
		return new JacksonUtils(objectMapper);
	}
}
