package com.tomato.order.infrastructure.repository.impl;

import com.tomato.account.api.RemoteAccountService;
import com.tomato.account.vo.req.AccountTradReq;
import com.tomato.order.domain.domain.entity.AccountEntity;
import com.tomato.order.domain.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 账户仓储
 *
 * @author lizhifu
 * @since 2023/8/10
 */
@Repository
@Slf4j
public class AccountRepositoryImpl implements AccountRepository {
    private final RemoteAccountService remoteAccountService;

    public AccountRepositoryImpl(RemoteAccountService remoteAccountService) {
        this.remoteAccountService = remoteAccountService;
    }

    @Override
    public void trad(AccountEntity accountEntity) {
        AccountTradReq accountTradReq = AccountTradReq.builder()
                .merchantNo(accountEntity.getMerchantNo())
                .amount(accountEntity.getAmount())
                .sysNo(accountEntity.getSysNo())
                .merchantOrderNo(accountEntity.getMerchantOrderNo())
                .build();
        remoteAccountService.trad(accountTradReq);
    }
}
