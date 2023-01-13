package com.tomato.account.domain.entity;

import com.tomato.domain.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDate;

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
     * 账户结算记录ID
     */
    private Long settleRecordId;

    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 下次结算日期
     */
    private LocalDate nextSettleDate;
}
