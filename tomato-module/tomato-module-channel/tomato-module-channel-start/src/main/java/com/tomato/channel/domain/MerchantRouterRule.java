package com.tomato.channel.domain;

import cn.mybatis.mp.db.annotations.Table;
import com.tomato.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商户绑定路由规则表，用于存储商户和路由规则之间的关联关系
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Getter
@Setter
@ToString
@Table(value = "t_merchant_router_rule")
public class MerchantRouterRule extends BaseEntity {
	/**
	 * 商户号
	 */
    private String merchantNo;

	/**
	 * 路由规则编号
	 */
    private String ruleNo;

	/**
	 * 绑定类型 默认或备份
	 */
    private String bindingType;

	/**
	 * 支付类型
	 */
	private String payType;

	/**
	 * 状态
	 */
	private String status;
}
