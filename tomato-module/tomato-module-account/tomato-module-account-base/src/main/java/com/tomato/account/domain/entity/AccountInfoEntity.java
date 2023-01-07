package com.tomato.account.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Data
public class AccountInfoEntity extends BaseEntity {
    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 账户历史流水顺序号
     */
    private Long accountHisSerial;

    /**
     * 账户管理顺序号
     */
    private Long accountManageSerial;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 风险预存期外余额
     */
    private BigDecimal outReserveBalance;

    /**
     * 上日账户余额
     */
    private BigDecimal yesterdayBalance;

    /**
     * 上一次交易日期
     */
    private LocalDateTime lastTradTime;

    /**
     * 账户状态
     */
    private String accountStatus;
}
