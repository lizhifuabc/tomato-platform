package com.tomato.account.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 账户结算控制
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Data
public class AccountSettleControlEntity extends BaseEntity {

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
     * 结算状态
     */
    private String settleStatus;

    /**
     * 上次结算批次
     */
    private String lastSettleBatch;

    /**
     * 上次结算日期
     */
    private LocalDateTime lastSettleTime;

    /**
     * 上次结算执行日期
     */
    private LocalDateTime lastExecuteTime;

    /**
     * 上次汇总日期
     */
    private LocalDateTime lastCollectTime;

    /**
     * 下次结算日期
     */
    private LocalDate nextSettleDate;
}
