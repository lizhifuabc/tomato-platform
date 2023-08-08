package com.tomato.order.domain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 渠道信息
 *
 * @author lizhifu
 * @since 2023/8/5
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelEntity {
    /**
     * 扫码地址
     */
    private String scanUrl;
    /**
     * 渠道编号
     */
    private String channelNo;
}
