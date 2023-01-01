package com.tomato.account.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Data
public class AccountEntity extends BaseEntity {
    /**
     * 账户编号
     */
    private String accountNo;
    /**
     * 账户余额
     */
    private BigDecimal balance;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 商户编号
     */
    private String merchantNo;
    /**
     * 风险预存期天数
     */
    private Integer riskDay;
    /**
     * 账户类型
     */
    private String accountType;
}
