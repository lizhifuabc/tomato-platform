package com.tomato.account.domain.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户历史汇总
 *
 * @author lizhifu
 * @date 2022/7/13
 */
@Data
public class AccountHisCollectResBO {
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 总笔数
     */
    private Integer totalCount;
}
