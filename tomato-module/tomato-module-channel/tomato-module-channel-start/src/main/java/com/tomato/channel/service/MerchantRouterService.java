package com.tomato.channel.service;

import com.tomato.channel.domain.MerchantRouterRule;
import com.tomato.channel.mapper.MerchantRouterRuleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户通道筛选
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Service
public class MerchantRouterService {
	private final MerchantRouterRuleMapper merchantRouterRuleMapper;

	public MerchantRouterService(MerchantRouterRuleMapper merchantRouterRuleMapper) {
		this.merchantRouterRuleMapper = merchantRouterRuleMapper;
	}

	public void route(String merchantNo,String payType){
		// 筛选所有的路由
		List<MerchantRouterRule> merchantRouterRules = merchantRouterRuleMapper.selectByMerchant(merchantNo, payType);

	}
}
