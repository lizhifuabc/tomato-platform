package com.tomato.channel.api.fallback;

import com.tomato.channel.api.RemoteChannelService;
import com.tomato.common.resp.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Fallback 处理类
 * FallbackFactory ： 导致回退触发的原因
 * @author lizhifu
 * @since 2023/8/3
 */
@Slf4j
@Component
public class RemoteChannelServiceFallback implements FallbackFactory<RemoteChannelService>{

    @Override
    public RemoteChannelService create(Throwable cause) {
        return channelReq -> {
            log.error("远程RemoteChannelService服务调用失败",cause);
            return Resp.buildFailure(cause.getMessage());
        };
    }
}
