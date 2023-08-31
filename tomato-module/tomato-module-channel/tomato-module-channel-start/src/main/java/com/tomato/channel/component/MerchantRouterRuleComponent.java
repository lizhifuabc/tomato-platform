package com.tomato.channel.component;

import com.tomato.channel.domain.MerchantRouterRule;
import com.tomato.channel.mapper.MerchantRouterRuleMapper;
import org.springframework.stereotype.Component;


/**
 * 商户绑定路由规则表
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Component
public class MerchantRouterRuleComponent {
	private final MerchantRouterRuleMapper merchantRouterRuleMapper;

	public MerchantRouterRuleComponent(MerchantRouterRuleMapper merchantRouterRuleMapper) {
		this.merchantRouterRuleMapper = merchantRouterRuleMapper;
	}

	/**
	 * 创建 商户路由规则
	 * @param merchantNo 商户编号
	 * @param bindingType 绑定类型
	 * @param payType 支付类型
	 */
	public void createMerchantRouterRule(String merchantNo, String bindingType,String payType){
		MerchantRouterRule merchantRouterRule = new MerchantRouterRule();
		merchantRouterRule.setMerchantNo(merchantNo);
		merchantRouterRule.setRuleNo(merchantNo + "_" + payType + "_" + bindingType);
        merchantRouterRule.setBindingType(bindingType);
        merchantRouterRule.setPayType(payType);
		merchantRouterRuleMapper.insertSelective(merchantRouterRule);
	}
}
