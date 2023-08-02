package com.tomato.merchant.api;

import com.tomato.common.resp.Resp;
import com.tomato.merchant.domain.req.MerchantTradReq;
import com.tomato.merchant.domain.resp.MerchantTradResp;
import com.tomato.module.common.constants.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
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
@Component
@FeignClient(contextId = "remoteMerchantService", value = ServiceNameConstants.SERVICE_NAME_MERCHANT)
public interface RemoteMerchantService {
    /**
     * 商户交易
     * @param merchantTradReq 商户交易
     * @return 商户交易
     */
    @RequestMapping(value = "/merchant/trade", method = RequestMethod.POST)
    Resp<MerchantTradResp> trade(@RequestBody MerchantTradReq merchantTradReq);
}
