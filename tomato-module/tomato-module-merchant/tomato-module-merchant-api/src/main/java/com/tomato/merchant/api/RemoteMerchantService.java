package com.tomato.merchant.api;

import com.tomato.common.resp.Resp;
import com.tomato.merchant.api.fallback.RemoteMerchantServiceFallback;
import com.tomato.merchant.domain.req.MerchantTradReq;
import com.tomato.merchant.domain.resp.MerchantTradResp;
import com.tomato.common.constants.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
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
}
