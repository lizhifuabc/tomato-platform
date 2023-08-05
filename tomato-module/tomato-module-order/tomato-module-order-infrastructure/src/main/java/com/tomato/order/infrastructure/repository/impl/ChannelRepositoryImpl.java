package com.tomato.order.infrastructure.repository.impl;

import com.tomato.channel.api.RemoteChannelService;
import com.tomato.channel.api.req.ChannelReq;
import com.tomato.channel.api.resp.ChannelResp;
import com.tomato.common.resp.Resp;
import com.tomato.order.domain.domain.entity.ChannelEntity;
import com.tomato.order.domain.repository.ChannelRepository;
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
        Resp<ChannelResp> trade = remoteChannelService.trade(channelReq);
        if (trade.isSuccess()) {
            ChannelResp data = trade.getData();
            ChannelEntity re = new ChannelEntity();
            return re;
        }
        throw new RuntimeException(trade.getMsg());
    }
}
