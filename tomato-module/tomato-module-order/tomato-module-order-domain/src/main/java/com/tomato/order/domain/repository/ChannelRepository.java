package com.tomato.order.domain.repository;

import com.tomato.order.domain.domain.entity.ChannelEntity;

/**
 * 渠道相关
 *
 * @author lizhifu
 * @since 2023/8/5
 */
public interface ChannelRepository {
    ChannelEntity tradeChannel(Integer payType, String merchantNo);
}
