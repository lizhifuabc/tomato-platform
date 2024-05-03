package com.tomato.cloud.gateway.handler;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.ApplicationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 服务变更监听
 *
 * @author lizhifu
 * @since 2023/7/19
 */
@Slf4j
public class RefreshRoutesListener implements ApplicationListener<RefreshRoutesEvent> {

	/**
	 * Swagger API URI
	 */
	public static final String API_URI = "/v3/api-docs";
	/**
	 * 应用名称
	 */
	@Value("${spring.application.name}")
	private String applicationName;
	/**
	 * 路由定位
	 */
	private final RouteLocator routeLocator;
	/**
	 * SwaggerUi配置参数
	 */
	private final SwaggerUiConfigParameters swaggerUiConfigParameters;
	/**
	 * SwaggerUi配置
	 */
	private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public RefreshRoutesListener(RouteLocator routeLocator, SwaggerUiConfigParameters swaggerUiConfigParameters, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.routeLocator = routeLocator;
        this.swaggerUiConfigParameters = swaggerUiConfigParameters;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @Override
	public void onApplicationEvent(@NotNull RefreshRoutesEvent event) {
		// 1.获取路由信息
		List<String> routes = new ArrayList<>();
		routeLocator.getRoutes()
				.filter(route -> route.getUri().getHost() != null && Objects.equals(route.getUri().getScheme(), "lb") && !applicationName.equalsIgnoreCase(route.getUri().getHost()))
				.subscribe(route -> routes.add(route.getUri().getHost()));
		log.info("路由信息:{}", routes);
		// 2.刷新Swagger
		Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = routes.stream().map(this::createSwaggerUrl).collect(Collectors.toSet());
		if (swaggerUiConfigParameters != null && swaggerUiConfigProperties.isEnabled()) {
			swaggerUiConfigParameters.setUrls(swaggerUrls);
			swaggerUiConfigParameters.setUrls(swaggerUiConfigProperties.getUrls());
			log.info("刷新Swagger成功");
		}
	}

	private AbstractSwaggerUiConfigProperties.SwaggerUrl createSwaggerUrl(String service) {
        String url = "/" + service.toLowerCase() + API_URI;
		AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
		swaggerUrl.setUrl(url);
		swaggerUrl.setName(service);
		return swaggerUrl;
	}
}
