package com.tomato.cloud.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 黑名单配置,运行时刷新
 *
 * @author lizhifu
 * @since 2023/8/29
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties("tomato.black-list")
public class BlackListProperties {

	/**
	 * 是否开启
	 */
	private Boolean enable = true;

	/**
	 * 黑名单IP
	 */
	private List<String> ipList = Collections.emptyList();

	/**
	 * 黑名单IP与服务
	 */
	private List<BlackList> services = Collections.emptyList();

	@Data
	public static class BlackList {

		/**
		 * IP
		 */
		private String ip;

		/**
		 * 服务名
		 */
		private String name;

		/**
		 * 路径 默认拦截所有：/**
		 */
		private List<String> pathList = Collections.singletonList("/**");

	}

}
