package com.tomato.jackson.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
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
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		//设置可见性
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 默认键入对象
		// 将类名称序列化到json串中
		// json格式序列化时会将对象类名称信息也会序列化进来，反序列化时同样也是需要 类和值的信息，格式是一个数组（两个长度），
		// 数组[0]存储类的信息，数组[1]存储值信息，这也印证了为什么我们错误提示是 Unexpected token (START_OBJECT), expected START_ARRAY。
		// mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}
