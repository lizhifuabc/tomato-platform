package com.tomato.account.domain.entity;

import com.tomato.domain.core.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户历史表
 *
 * @author lizhifu
 * @date 2022/6/7
 */
@Data
public class AccountHisEntity extends BaseEntity {
    /**
     * 商户编号
     */
    private String merchantNo;
    /**
     * 商户订单号
     */
    private String merchantOrderNo;
    /**
     * 账户历史流水顺序号
     */
    private Long accountHisSerial;

    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 发生前余额
     */
    private BigDecimal beforeBalance;

    /**
     * 发生后余额
     */
    private BigDecimal afterBalance;

    /**
     * 发生金额
     */
    private BigDecimal amount;
    /**
     * 手续费
     */
    private BigDecimal amountFree;

    /**
     * 第三方流水号
     */
    private String thirdNo;

    /**
     * 类型
     */
    private String accountHisType;

    /**
     * 入账完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 入账状态
     */
    private String accountStatus;
}
