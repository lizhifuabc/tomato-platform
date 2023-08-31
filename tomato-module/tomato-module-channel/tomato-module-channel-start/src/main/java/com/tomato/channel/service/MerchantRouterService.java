package com.tomato.channel.service;

import com.tomato.channel.component.MerchantRouterRuleComponent;
import com.tomato.channel.component.PayChannelComponent;
import com.tomato.channel.component.RouterRuleComponent;
import com.tomato.channel.domain.MerchantRouterRule;
import com.tomato.channel.domain.PayChannel;
import com.tomato.channel.domain.RouterRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 商户通道筛选
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Service
@Slf4j
public class MerchantRouterService {
	private final MerchantRouterRuleComponent merchantRouterRuleComponent;
	private final RouterRuleComponent routerRuleComponent;

	private final PayChannelComponent payChannelComponent;
	public MerchantRouterService(MerchantRouterRuleComponent merchantRouterRuleComponent, RouterRuleComponent routerRuleComponent, PayChannelComponent payChannelComponent) {
		this.merchantRouterRuleComponent = merchantRouterRuleComponent;
		this.routerRuleComponent = routerRuleComponent;
		this.payChannelComponent = payChannelComponent;
	}

	public void route(String merchantNo,String payType){
		// 筛选路由
		MerchantRouterRule merchantRouterRule= merchantRouterRuleComponent.filterMerchantRouterRule(merchantNo, payType);
		log.info("商户：{},渠道：{}，筛选结果：{}",merchantNo, payType,merchantRouterRule);
		// 获取路由规则信息
		RouterRule routerRule = routerRuleComponent.filterRouterRule(merchantRouterRule.getRuleNo());
		log.info("商户：{},渠道：{}，路由规则：{}",merchantNo, payType,routerRule);
		// 路由信息息
		PayChannel payChannel = payChannelComponent.filterPayChannel(routerRule.getRuleNo(),routerRule.getFilterType());
		log.info("商户：{},渠道：{}，路由信息：{}",merchantNo, payType,payChannel);
	}
}
