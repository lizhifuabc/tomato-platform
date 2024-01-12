package com.tomato.account.api.fallback;

import com.tomato.account.api.RemoteAccountService;
import com.tomato.common.domain.resp.Resp;
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
public class RemoteAccountServiceFallback implements FallbackFactory<RemoteAccountService> {

	@Override
	public RemoteAccountService create(Throwable cause) {
		return accountTradReq -> {
			log.error("远程RemoteAccountService服务调用失败", cause);
			return Resp.buildFailure(cause.getMessage());
		};
	}

}
