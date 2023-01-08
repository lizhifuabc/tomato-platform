package com.tomato.account.domain.req;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 目标银行卡(结算到银行卡使用)
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Data
public class AccountBankCardCreateReq {
    /**
     * 银行代码(结算到银行卡必须)
     */
    private String bankCode;

    /**
     * 银行名称(结算到银行卡必须)
     */
    private String bankName;

    /**
     * 银行地址
     */
    private String bankAddress;

    /**
     * 银行分行号
     */
    private String bankBranch;

    /**
     * 银行卡号(结算到银行卡必须)
     */
    private String cardNo;

    /**
     * 银行开户姓名(结算到银行卡必须)
     */
    private String accountName;

    /**
     * 银行卡类型(结算到银行卡必须)
     * @link BankCardAttributeEnum
     */
    private String cardType;

    /**
     * 省
     */
    private String province;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市
     */
    private String city;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 上次结算日期 TODO
     */
    private Date lastSettleDate;

    /**
     * 账户出款比例 TODO
     */
    private BigDecimal outRatio;
}
