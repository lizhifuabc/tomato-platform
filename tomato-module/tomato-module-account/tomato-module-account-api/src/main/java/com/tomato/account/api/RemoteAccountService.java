package com.tomato.account.api;

import com.tomato.account.api.fallback.RemoteAccountServiceFallback;
import com.tomato.account.vo.req.AccountTradReq;
import com.tomato.common.resp.Resp;
import com.tomato.common.constants.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 远程 Account 服务
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@FeignClient(contextId = "remoteAccountService", value = ServiceNameConstants.SERVICE_NAME_ACCOUNT,
        fallbackFactory = RemoteAccountServiceFallback.class,dismiss404 = true)
public interface RemoteAccountService {
    /**
     * 账户入账
     * @param accountTradReq 入账请求
     * @return Resp 账户入账结果
     */
    @PostMapping("/account/trad")
    Resp<Void> trad(@RequestBody AccountTradReq accountTradReq);
}
