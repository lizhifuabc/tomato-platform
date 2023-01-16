package com.tomato.account.service;

import com.tomato.account.domain.bo.AccountRefundBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 账户退款
 * 1. 退款直接退入风险外金额
 * 2. 是否存在风内金额出款的操作 TODO
 * @author lizhifu
 * @since 2023/1/11
 */
@Service
@Slf4j
public class AccountRefundService {
    /**
     * 退款
     * @param accountRefundBO
     */
    public void refund(AccountRefundBO accountRefundBO){
        log.info("账户发起退款start:[{}]", accountRefundBO);

        log.info("账户发起退款end:[{}]", accountRefundBO);
    }
}
