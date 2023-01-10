package com.tomato.account.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Data
public class AccountSettleEntity extends BaseEntity {
    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 结算类型
     */
    private String settleType;

    /**
     * 结算周期
     */
    private String cycleType;

    /**
     * 结算周期数据
     */
    private String cycleData;

    /**
     * 风险预存期
     */
    private Integer reserveDays;

    /**
     * 最小结算金额
     */
    private BigDecimal minAmount;

    /**
     * 是否承担划款手续费标志:0-否， 1-是
     */
    private Integer settleFeeFlag;

    /**
     * 客户不承担手续费限额
     */
    private BigDecimal limitSettleFee;

    /**
     * 结算手续费
     */
    private BigDecimal settleFee;

    /**
     * 封顶手续费
     */
    private BigDecimal maxSettleFee;

    /**
     * 最大结算天数
     */
    private Integer maxSettleDays;

    /**
     * 结算到目标账户类型
     */
    private String settleTargetType;

    /**
     * 备注编码
     */
    private String remarkCode;

    /**
     * 备注
     */
    private String remarkCaption;

    /**
     * 打款备注的表达式，可以动态计算
     */
    private String remitMemo;

    /**
     * 打款备注
     */
    private String remitMemoFormula;
}
