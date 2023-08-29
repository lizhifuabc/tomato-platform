package com.tomato.order.infrastructure.repository.impl;

import com.tomato.channel.api.RemoteChannelService;
import com.tomato.channel.vo.req.ChannelReq;
import com.tomato.channel.vo.resp.ChannelScanResp;
import com.tomato.common.resp.Resp;
import com.tomato.order.domain.domain.entity.ChannelEntity;
import com.tomato.order.domain.repository.ChannelRepository;
import com.tomato.web.core.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 渠道相关
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Repository
@Slf4j
public class ChannelRepositoryImpl implements ChannelRepository {

	private final RemoteChannelService remoteChannelService;

	public ChannelRepositoryImpl(RemoteChannelService remoteChannelService) {
		this.remoteChannelService = remoteChannelService;
	}

	@Override
	public ChannelEntity tradeChannel(Integer payType, String merchantNo) {
		ChannelReq channelReq = new ChannelReq();
		channelReq.setPayType(payType);
		channelReq.setMerchantNo(merchantNo);
		Resp<ChannelScanResp> trade = remoteChannelService.tradeScan(channelReq);
		if (trade.isSuccess()) {
			ChannelScanResp data = trade.getData();
			ChannelEntity channelEntity = new ChannelEntity();
			BeanUtil.copyProperties(data, channelEntity);
			return channelEntity;
		}
		throw new RuntimeException(trade.getMsg());
	}

}
