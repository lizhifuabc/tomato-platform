package com.tomato.account.service.trad;

import com.tomato.account.domain.req.AccountTradReq;

/**
 * 交易服务
 *
 * @author lizhifu
 * @since 2023/4/17
 */
public interface AccountTradService {
    /**
     * 账户入账同步
     * @param accountTradReq 入账请求
     */
    void exe(AccountTradReq accountTradReq);
    /**
     * 账户入账异步
     * @param accountTradReq 入账请求
     */
    void exeAsync(AccountTradReq accountTradReq);
}
