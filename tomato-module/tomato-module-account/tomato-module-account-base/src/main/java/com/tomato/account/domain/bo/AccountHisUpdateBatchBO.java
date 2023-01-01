package com.tomato.account.domain.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * AccountHisUpdateDO
 *
 * @author lizhifu
 * @date 2022/6/7
 */
@Data
public class AccountHisUpdateBatchBO {
    /**
     * 账号编号
     */
    private String accountNo;
    /**
     * 账户历史表ID
     */
    private List accountHisId;
    /**
     * 入账状态
     */
    private Integer accountStatus;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 发生前余额
     */
    private BigDecimal beforeBalance;
    /**
     * 发生后余额
     */
    private BigDecimal afterBalance;
}
