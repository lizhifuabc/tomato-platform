package com.tomato.cloud.gateway.gray.properties;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.alibaba.cloud.nacos.client.NacosPropertySource;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 灰度配置
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@ConfigurationProperties(prefix = "gray")
@NacosConfigurationProperties(dataId = "gray", autoRefreshed = true)
@Data
public class GrayProperties {

	private boolean enabled = false;

	private List<GrayServiceInfo> services = Collections.emptyList();

	/**
	 * 灰度服务信息
	 */
	@Data
	public static class GrayServiceInfo {

		private String serviceId;

		private boolean weightEnabled = false;

		private List<GrayVersionInfo> versions = Collections.emptyList();

		/**
		 * 灰度版本信息
		 */
		@Data
		public static class GrayVersionInfo {

			private String version;

			private Map<String, List<String>> headerParam = Collections.emptyMap();

			private double weight;

		}

	}

	/**
	 * 获取灰度服务
	 * @param serviceId 服务ID
	 * @return GrayService 灰度服务
	 */
	public GrayServiceInfo getGrayServiceInfo(String serviceId) {
		return services == null ? null
				: services.stream().filter(i -> i.getServiceId().equals(serviceId)).findFirst().orElse(null);
	}

}
