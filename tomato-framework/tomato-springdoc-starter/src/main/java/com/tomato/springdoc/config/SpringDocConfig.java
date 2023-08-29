package com.tomato.springdoc.config;

import com.tomato.springdoc.properties.SpringDocProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring doc 配置
 *
 * @author lizhifu
 * @since 2023/4/10
 */
@Configuration
@EnableConfigurationProperties(SpringDocProperties.class)
@ConditionalOnProperty(name = { "springdoc.api-docs.enabled" }, matchIfMissing = true)
public class SpringDocConfig {

	@Bean
	@ConditionalOnMissingBean
	public OpenAPI createOpenApi() {
		return new OpenAPI()
			.info(new Info().title("西红柿土豆安安")
				.description("西红柿土豆安安")
				.version("Swagger V3")
				.license(new License().name("WTFPL license").url("http://www.wtfpl.net/")))
			.externalDocs(new ExternalDocumentation().description("西红柿土豆安安文档")
				.url("https://github.com/lizhifuabc/tomato-platform"));
	}

}
