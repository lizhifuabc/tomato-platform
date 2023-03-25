package com.tomato.seckill.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger文档，只有在测试环境才会使用
 * @author lizhifu
 * @since 2023/03/25
 */
@Configuration
public class SwaggerConfiguration {
	@Bean
	public OpenAPI springOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("秒杀服务接口文档")
						.description("秒杀服务接口文档Swagger版")
						.version("v0.0.1"));
	}

}
