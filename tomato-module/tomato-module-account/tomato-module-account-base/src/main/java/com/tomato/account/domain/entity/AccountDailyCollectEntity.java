package com.tomato.account.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 每日待结算汇总
 *
 * @author lizhifu
 * @since  2022/7/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountDailyCollectEntity extends BaseEntity {
    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 汇总日期
     */
    private LocalDate collectDate;

    /**
     * 交易总金额
     */
    private BigDecimal totalAmount;

    /**
     * 交易总笔数
     */
    private Long totalCount;
}
