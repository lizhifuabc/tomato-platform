package com.tomato.merchant.controller;

import com.tomato.domain.resp.Resp;
import com.tomato.merchant.domain.req.MerchantCreateReq;
import com.tomato.merchant.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户创建
 *
 * @author lizhifu
 * @date 2022/12/13
 */
@RestController
@RequestMapping
public class MerchantCreateController {
    private final MerchantService merchantService;

    public MerchantCreateController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping("/merchant/create")
    public Resp create(@Valid @RequestBody MerchantCreateReq merchantCreateReq){
        merchantService.createMerchant(merchantCreateReq);
        return Resp.buildSuccess();
    }
}
