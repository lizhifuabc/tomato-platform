package com.tomato.channel.domain;

import com.tomato.common.domain.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 路由规则表
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Getter
@Setter
@ToString
@Entity(name = "t_router_rule")
public class RouterRule extends BaseEntity {
	/**
	 * 路由规则编号
	 */
	private String ruleNo;

	/**
	 * 路由规则名称
	 */
	private String ruleName;

	/**
	 * 支付方式
	 */
	private String payType;

	/**
	 * 路由规则描述
	 */
	private String ruleDescription;

	/**
	 * 状态，active表示可用，inactive表示不可用
	 */
	private String status;

	/**
	 * 筛选方式，可以是成本、响应时间或权重
	 */
	private String filterType;
}
