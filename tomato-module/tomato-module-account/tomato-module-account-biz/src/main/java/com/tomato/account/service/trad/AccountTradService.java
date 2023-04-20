package com.tomato.account.service.trad;

import com.tomato.account.domain.dto.AccountTradDto;

/**
 * 账户交易服务
 * <p>加钱减钱</p>
 *
 * @author lizhifu
 * @since 2023/4/17
 */
public interface AccountTradService {
    /**
     * 账户交易同步
     * @param accountTradDto 交易请求
     */
    void exe(AccountTradDto accountTradDto);
    /**
     * 账户交易异步
     * @param accountTradDto 交易请求
     */
    void exeAsync(AccountTradDto accountTradDto);
}
