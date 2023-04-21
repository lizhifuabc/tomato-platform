package com.tomato.account.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;

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
     * 最大结算天数
     */
    private Integer maxSettleDays;

    /**
     * 结算到目标账户类型
     */
    private String settleTargetType;
    /**
     * 备注
     */
    private String remark;
}
