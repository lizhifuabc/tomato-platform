package com.tomato.channel.controller;

import com.tomato.channel.event.ChannelRedisEvent;
import com.tomato.channel.event.ChannelRedisEventData;
import com.tomato.channel.vo.req.ChannelReq;
import com.tomato.channel.vo.resp.ChannelScanResp;
import com.tomato.common.resp.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * 通道
 *
 * @author lizhifu
 * @since 2023/8/8
 */
@RestController
@RequestMapping
@Slf4j
@Tags({
        @Tag(name = "渠道交易"),
})
public class ChannelController {
    private final ApplicationContext applicationContext;
    public ChannelController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 渠道交易
     * @param channelReq 请求
     * @return 渠道交易
     */
    @Operation(summary = "扫码请求", description = "扫码请求")
    @RequestMapping(value = "/channel/tradeScan",method = RequestMethod.POST)
    public Resp<ChannelScanResp> tradeScan(@Valid @RequestBody ChannelReq channelReq){
        log.info("渠道交易:{}",channelReq);
        ChannelScanResp channelScanResp = new ChannelScanResp();
        channelScanResp.setChannelNo("1630000000000");
        channelScanResp.setScanUrl(String.valueOf(System.currentTimeMillis()));

        ChannelRedisEventData channelRedisEventData = ChannelRedisEventData.builder()
                .payType(channelReq.getPayType().toString())
                .resultType("SUCCESS")
                .channelNo(channelScanResp.getChannelNo()).build();
        // 随机模拟成功或者失败
        if(System.currentTimeMillis() % 2 == 0){
            channelRedisEventData.setResultType("FAIL");
            applicationContext.publishEvent(new ChannelRedisEvent(channelRedisEventData));
            return Resp.buildFailure("渠道交易失败");
        }
        applicationContext.publishEvent(new ChannelRedisEvent(channelRedisEventData));
        return Resp.of(channelScanResp);
    }
}
