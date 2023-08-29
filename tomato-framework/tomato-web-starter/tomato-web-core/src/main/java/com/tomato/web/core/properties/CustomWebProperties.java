package com.tomato.web.core.properties;

import com.tomato.web.core.constants.WebConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义web配置
 *
 * @author lizhifu
 * @since 2023/4/8
 */
@Data
@ConfigurationProperties(prefix = WebConstants.PROPERTY_PREFIX_WEB)
public class CustomWebProperties {

	/**
	 * 是否开启controller切面
	 */
	private Boolean controllerPointEnable = true;

}
