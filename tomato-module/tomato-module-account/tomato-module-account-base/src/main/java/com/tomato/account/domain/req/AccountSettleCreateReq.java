package com.tomato.account.domain.req;

import lombok.Data;

/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Data
public class AccountSettleCreateReq {
    /**
     * 账户编号
     */
    private String accountNo;
    /**
     * 商户编号
     */
    private String merchantNo;
}
