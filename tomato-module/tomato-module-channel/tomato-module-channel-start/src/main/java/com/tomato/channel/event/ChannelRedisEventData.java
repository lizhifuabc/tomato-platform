package com.tomato.channel.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>渠道 redis 事件</p>
 *
 * @author lizhifu
 * @since 2023/8/26
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChannelRedisEventData {
    String payType;
    String channelNo;
    String resultType;
}
