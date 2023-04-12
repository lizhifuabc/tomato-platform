package com.tomato.order.domain.domain;

/**
 * 订单查询
 *
 * @author lizhifu
 * @since 2023/4/7
 */
public class OrderQueryDomain {
    private String merchantNo;
    private String merchantOrderNo;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }
}
