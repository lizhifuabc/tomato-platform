package com.tomato.account.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 每日待结算汇总
 *
 * @author lizhifu
 * @date 2022/7/13
 */
@Data
public class AccountDailyCollectEntity extends BaseEntity {
    /**
     * ID
     */
    private Long accountDailyCollectId;
    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 版本号
     */
    private Integer version;
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
    private Integer totalCount;

    /**
     * 结算状态【0->已结算；1->未结算】
     */
    private Integer settStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 风险预存期天数
     */
    private Integer riskDay;

    public AccountDailyCollectEntity() {}
}
