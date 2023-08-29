package com.tomato.rpc.common.domain;

import lombok.Data;

/**
 * 服务元数据，注册到注册中心的元数据信息
 *
 * @author lizhifu
 * @since 2023/5/26
 */
@Data
public class ServiceMetadata {

	/**
	 * 名称
	 */
	private String serviceName;

	/**
	 * 版本号
	 */
	private String serviceVersion;

	/**
	 * 地址
	 */
	private String serviceAddr;

	/**
	 * 端口号
	 */
	private int servicePort;

	/**
	 * 分组
	 */
	private String serviceGroup;

	/**
	 * 权重
	 */
	private int weight;

	/**
	 * 连接数
	 */
	private int connectionCount;

}
