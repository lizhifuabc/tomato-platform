package com.tomato.channel.component;

import com.tomato.channel.domain.MerchantRouterRule;
import com.tomato.channel.mapper.MerchantRouterRuleMapper;
import com.tomato.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


/**
 * 商户绑定路由规则表
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Component
@Slf4j
public class MerchantRouterRuleComponent {
	private final MerchantRouterRuleMapper merchantRouterRuleMapper;

	public MerchantRouterRuleComponent(MerchantRouterRuleMapper merchantRouterRuleMapper) {
		this.merchantRouterRuleMapper = merchantRouterRuleMapper;
	}

	/**
	 * 创建商户路由规则
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

	/**
	 * 筛选商户路由规则
	 * @param merchantNo 商户编号
	 * @param payType 支付类型
	 * @return 商户路由规则
	 */
	public MerchantRouterRule filterMerchantRouterRule(String merchantNo, String payType) {
		List<MerchantRouterRule> merchantRouterRules = merchantRouterRuleMapper.selectByMerchant(merchantNo, payType);
		Optional.ofNullable(merchantRouterRules).orElseThrow(() -> new BusinessException("找不到路由器规则:"+merchantNo + "_"+payType));
		return merchantRouterRules.get(0);
	}
}
