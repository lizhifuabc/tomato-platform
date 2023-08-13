package com.tomato.merchant.controller;

import com.tomato.common.resp.Resp;
import com.tomato.merchant.domain.req.MerchantRateReq;
import com.tomato.merchant.service.MerchantRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户费率
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@RestController
@RequestMapping
@Tags({
        @Tag(name = "商户费率"),
})
public class MerchantRateController {
    private final MerchantRateService merchantRateService;

    public MerchantRateController(MerchantRateService merchantRateService) {
        this.merchantRateService = merchantRateService;
    }

    /**
     * 商户费率
     * @param merchantRateReq 商户费率
     * @return Resp<Void>
     */
    @PostMapping("/merchant/rate")
    @Operation(summary = "商户费率", description = "商户费率")
    public Resp<Void> rate(@Valid @RequestBody MerchantRateReq merchantRateReq) {
        merchantRateService.rate(merchantRateReq);
        return Resp.buildSuccess();
    }
}
