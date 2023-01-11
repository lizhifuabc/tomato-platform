package com.tomato.account.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 账户结算记录
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Data
public class AccountSettleRecordEntity extends BaseEntity {
    /**
     * 账户结算规则id
     */
    private Long accountSettleId;

    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 结算日期
     */
    private LocalDate settleDate;

    /**
     * 结算金额
     */
    private BigDecimal settleAmount;

    /**
     * 结算手续费
     */
    private BigDecimal settleFee;
    /**
     * 结算手续费率
     */
    private BigDecimal settleRate;
}
