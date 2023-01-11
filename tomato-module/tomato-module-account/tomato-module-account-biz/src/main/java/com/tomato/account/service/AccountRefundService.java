package com.tomato.account.service;

import com.tomato.account.domain.req.AccountRefundReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 账户退款
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@Service
@Slf4j
public class AccountRefundService {
    /**
     * 退款
     * @param accountRefundReq
     */
    public void refund(AccountRefundReq accountRefundReq){
        log.info("账户发起退款start:[{}]",accountRefundReq);

        log.info("账户发起退款end:[{}]",accountRefundReq);
    }
}
