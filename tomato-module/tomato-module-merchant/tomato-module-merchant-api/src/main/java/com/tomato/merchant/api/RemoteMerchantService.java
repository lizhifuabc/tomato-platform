package com.tomato.merchant.api;

import com.tomato.common.resp.Resp;
import com.tomato.merchant.api.fallback.RemoteMerchantServiceFallback;
import com.tomato.merchant.domain.req.MerchantConfigQueryReq;
import com.tomato.merchant.domain.req.MerchantTradReq;
import com.tomato.merchant.domain.resp.MerchantConfigQueryResp;
import com.tomato.merchant.domain.resp.MerchantTradResp;
import com.tomato.module.common.constants.ServiceNameConstants;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 远程 MERCHANT 服务
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@FeignClient(contextId = "remoteMerchantService", value = ServiceNameConstants.SERVICE_NAME_MERCHANT,
        fallbackFactory = RemoteMerchantServiceFallback.class,dismiss404 = true)
public interface RemoteMerchantService {
    /**
     * 商户交易
     * @param merchantTradReq 商户交易
     * @return 商户交易
     */
    @RequestMapping(value = "/merchant/trade", method = RequestMethod.POST)
    Resp<MerchantTradResp> trade(@RequestBody MerchantTradReq merchantTradReq);
    /**
     * 商户配置
     * @param merchantConfigReq 商户配置
     * @return 商户配置
     */
    @PostMapping("/merchant/config/query")
    @Operation(summary = "商户配置查询", description = "商户配置查询")
    Resp<MerchantConfigQueryResp> query(@RequestBody MerchantConfigQueryReq merchantConfigReq);
}
