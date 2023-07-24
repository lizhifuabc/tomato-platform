package com.tomato.merchant.controller;

import com.tomato.common.resp.Resp;
import com.tomato.merchant.domain.req.MerchantTradReq;
import com.tomato.merchant.domain.resp.MerchantTradResp;
import com.tomato.merchant.service.MerchantTradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户交易
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@RestController
@RequestMapping
@Tags({
        @Tag(name = "商户交易"),
})
public class MerchantTradeController {
    private final MerchantTradeService merchantTradeService;

    public MerchantTradeController(MerchantTradeService merchantTradeService) {
        this.merchantTradeService = merchantTradeService;
    }

    @PostMapping("/merchant/trade")
    @Operation(summary = "商户交易", description = "商户交易")
    public Resp<MerchantTradResp> trade(@Valid @RequestBody MerchantTradReq merchantCreateReq){
        return Resp.of(merchantTradeService.trade(merchantCreateReq));
    }
}
