package com.tomato.merchant.controller;

import com.tomato.domain.resp.Resp;
import com.tomato.merchant.domain.req.MerchantInfoReq;
import com.tomato.merchant.domain.resp.MerchantInfoResp;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户信息
 *
 * @author lizhifu
 * @date 2022/11/25
 */
@RestController
@RequestMapping
public class MerchantController {
    /**
     * 校验商户信息并返回
     * @param merchantInfoReq
     * @return
     */
    @PostMapping("/merchant/checkMerchant")
    public Resp<MerchantInfoResp> checkMerchant(@Valid @RequestBody MerchantInfoReq merchantInfoReq){
        return Resp.of(null);
    }
}
