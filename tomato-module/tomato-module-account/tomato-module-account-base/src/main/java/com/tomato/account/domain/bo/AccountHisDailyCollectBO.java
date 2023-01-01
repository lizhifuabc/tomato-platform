package com.tomato.account.domain.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 日汇总账户待结算金额
 *
 * @author lizhifu
 * @date 2022/7/13
 */
@Data
public class AccountHisDailyCollectBO {
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 总笔数
     */
    private Integer totalCount;
}
