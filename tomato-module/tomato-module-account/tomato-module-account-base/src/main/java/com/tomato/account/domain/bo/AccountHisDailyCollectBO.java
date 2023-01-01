package com.tomato.account.domain.bo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 日汇总账户待结算金额
 *
 * @author lizhifu
 * @date 2022/7/13
 */
@Data
public class AccountHisDailyCollectBO {
    /**
     * 账户编号
     */
    private String accountNo;
    /**
     * 汇总日期
     */
    private LocalDate collectDate;
    /**
     * 风险时间
     */
    private Integer riskDay;
}
