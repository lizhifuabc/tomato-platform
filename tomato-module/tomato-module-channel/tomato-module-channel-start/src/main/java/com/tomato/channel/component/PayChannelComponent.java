package com.tomato.channel.component;

import com.tomato.channel.domain.PayChannel;
import com.tomato.channel.mapper.PayChannelMapper;
import com.tomato.common.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 渠道
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Component
public class PayChannelComponent {
	private final PayChannelMapper payChannelMapper;

    public PayChannelComponent(PayChannelMapper payChannelMapper) {
		this.payChannelMapper = payChannelMapper;
	}

	/**
	 * 筛选渠道
	 * @param ruleNo 规则编码
	 * @param filterType 筛选类型
	 * @return 渠道
	 */
	public PayChannel filterPayChannel(String ruleNo,String filterType) {
		List<PayChannel> payChannels = payChannelMapper.selectByRuleNo(ruleNo);
		if(payChannels.isEmpty()) {
			throw new BusinessException("跟由规则不存:"+ruleNo);
		}
		return payChannels.get(0);
	}
}
