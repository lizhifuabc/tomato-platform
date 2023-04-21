package com.tomato.account.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;

/**
 * 目标银行卡(结算到银行卡使用)
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Data
public class AccountBankCardEntity extends BaseEntity {
    /**
     * 账户编号
     */
    private String accountNo;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行地址
     */
    private String bankAddress;

    /**
     * 银行地址
     */
    private String bankBranch;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 银行开户姓名
     */
    private String accountName;

    /**
     * 银行卡类型(对私/对公)
     */
    private String cardType;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 是否默认结算银行卡(扩展字段)
     */
    private Integer settleFlag;

    /**
     * 备注
     */
    private String remark;
}
