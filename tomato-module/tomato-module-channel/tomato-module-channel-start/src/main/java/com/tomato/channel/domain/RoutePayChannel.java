package com.tomato.channel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 路由渠道返回
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Getter
@Setter
@ToString
public class RoutePayChannel extends PayChannel{

	/**
	 * 权重
	 */
	private Integer weight;
}
