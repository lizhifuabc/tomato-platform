package com.tomato.channel.controller;

import com.tomato.channel.vo.req.ChannelReq;
import com.tomato.channel.vo.resp.ChannelScanResp;
import com.tomato.common.resp.Resp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
        channelScanResp.setChannelNo(String.valueOf(System.currentTimeMillis()));
        channelScanResp.setScanUrl(String.valueOf(System.currentTimeMillis()));
        return Resp.of(channelScanResp);
    }
}
