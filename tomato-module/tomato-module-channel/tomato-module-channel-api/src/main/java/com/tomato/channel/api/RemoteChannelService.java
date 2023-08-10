package com.tomato.channel.api;

import com.tomato.channel.api.fallback.RemoteChannelServiceFallback;
import com.tomato.channel.vo.req.ChannelReq;
import com.tomato.channel.vo.resp.ChannelScanResp;
import com.tomato.common.resp.Resp;
import com.tomato.common.constants.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 远程 Channel 服务
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@FeignClient(contextId = "remoteChannelService", value = ServiceNameConstants.SERVICE_NAME_CHANNEL,
        fallbackFactory = RemoteChannelServiceFallback.class,dismiss404 = true)
public interface RemoteChannelService {
    /**
     * 渠道交易
     * @param channelReq 请求
     * @return 渠道交易
     */
    @RequestMapping(value = "/channel/trade", method = RequestMethod.POST)
    Resp<ChannelScanResp> tradeScan(@RequestBody ChannelReq channelReq);
}
