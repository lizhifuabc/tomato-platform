package com.tomato.merchant.api.fallback;

import com.tomato.common.domain.resp.Resp;
import com.tomato.merchant.api.RemoteMerchantService;
import com.tomato.merchant.domain.req.MerchantConfigQueryReq;
import com.tomato.merchant.domain.req.MerchantTradReq;
import com.tomato.merchant.domain.resp.MerchantConfigQueryResp;
import com.tomato.merchant.domain.resp.MerchantTradResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Fallback 处理类 FallbackFactory ： 导致回退触发的原因
 *
 * @author lizhifu
 * @since 2023/8/3
 */
@Slf4j
@Component
public class RemoteMerchantServiceFallback implements FallbackFactory<RemoteMerchantService> {

	@Override
	public RemoteMerchantService create(Throwable cause) {
		return new RemoteMerchantService() {
			@Override
			public Resp<MerchantTradResp> trade(MerchantTradReq merchantTradReq) {
				log.error("merchant trade fallback,reason was", cause);
				return Resp.buildFailure(cause.getMessage());
			}

			@Override
			public Resp<MerchantConfigQueryResp> queryConfig(MerchantConfigQueryReq merchantConfigReq) {
				log.error("merchant queryConfig fallback,reason was", cause);
				return Resp.buildFailure(cause.getMessage());
			}
		};
	}

}
