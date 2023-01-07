package com.tomato.account.domain.bo;

import com.tomato.account.constant.AccountHisTypeEnum;
import com.tomato.domain.type.YesNoTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账号资金变动
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@Data
@Builder
public class AccountChangeBO {
    /**
     * 商户编号
     */
    private String merchantNo;
    /**
     * 发生金额
     */
    private BigDecimal amount;
    /**
     * 第三方流水号
     */
    private String thirdNo;
    /**
     * 发生类型
     */
    private AccountHisTypeEnum accountHisType;

    /**
     * 是否允许结算【0->是；1->否】
     */
    private YesNoTypeEnum allowSett;
}
