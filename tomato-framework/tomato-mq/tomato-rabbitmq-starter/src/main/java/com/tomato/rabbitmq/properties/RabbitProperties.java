package com.tomato.rabbitmq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 绑定配置
 *
 * @author lizhifu
 * @since 2023/5/23
 */
@Data
@ConfigurationProperties("spring.rabbitmq")
public class RabbitProperties {

	/**
	 * 绑定配置
	 */
	List<RabbitInfo> rabbitInfoList;

}
