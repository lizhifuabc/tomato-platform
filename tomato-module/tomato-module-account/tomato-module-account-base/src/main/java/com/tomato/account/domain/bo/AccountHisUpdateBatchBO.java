package com.tomato.account.domain.bo;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * AccountHisUpdateDO
 *
 * @author lizhifu
 * @since  2022/6/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountHisUpdateBatchBO extends AccountHisDealBO{
    /**
     * 账户历史表ID
     */
    private List<String> accountHisIdList;
    /**
     * 账号
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
     * 账户历史流水顺序号
     */
    private Long accountHisSerial;
    /**
     * 完成时间
     */
    private LocalDateTime completeTime = LocalDateTime.now();
}
