package com.tomato.account.domain.entity;

import com.tomato.domain.entity.BaseEntity;
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
     * 账户历史表ID
     */
    private Long accountHisId;
    /**
     * 账户ID
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
     * 第三方流水号
     */
    private String thirdNo;
    /**
     * 状态,10:创建,11:成功,12:失败
     */
    private Integer state;
    /**
     * 发生类型
     */
    private String accountHisType;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 是否允许结算【0->是；1->否】
     */
    private Integer allowSett;
    /**
     * 是否完成结算【0->是；1->否】
     */
    private Integer completeSett;
    /**
     * 入账状态
     */
    private Integer accountStatus;
    private LocalDateTime completeTime;
}
