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
     * 结算方式
     */
    private String settleMode;

    /**
     * 结算批次号
     */
    private String settleBatchNo;

    /**
     * 上一次打款流水号
     */
    private String lastRemitNo;

    /**
     * 结算日期
     */
    private LocalDate settleDate;

    /**
     * 交易总数
     */
    private Integer tradeCount;

    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;

    /**
     * 交易手续费
     */
    private BigDecimal tradeFee;

    /**
     * 结算金额
     */
    private BigDecimal settleAmount;

    /**
     * 结算手续费
     */
    private BigDecimal settleFee;

    /**
     * 打款金额
     */
    private BigDecimal remitAmount;

    /**
     * 结算开始日期
     */
    private LocalDate startDate;

    /**
     * 结算结束日期
     */
    private LocalDate endDate;
}
