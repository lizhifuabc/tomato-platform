package com.tomato.merchant.domain.entity;

import com.tomato.jpa.domain.entity.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * 商户费率表
 * TODO 商户费率通道关联
 *
 * @author lizhifu
 * @since  2022/11/25
 */
@Entity
@Table(name = "t_merchant_rate",uniqueConstraints = {
        @UniqueConstraint(columnNames={"merchant_no", "pay_type"})
})
public class MerchantRate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 商户编号
     */
    @Column(length = 64,nullable = false,name = "merchant_no")
    private String merchantNo;

    /**
     * 费率
     */
    @Column(nullable = false,precision = 20, scale = 6)
    private BigDecimal rate;

    /**
     * 是否停用: 0-否, 1-是
     */
    @Column(nullable = false,columnDefinition = "tinyint(0) not null default 0",insertable = false)
    private Integer merchantRateStatus;

    /**
     * 支付方式
     */
    @Column(nullable = false,name = "pay_type")
    private Integer payType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getMerchantRateStatus() {
        return merchantRateStatus;
    }

    public void setMerchantRateStatus(Integer merchantRateStatus) {
        this.merchantRateStatus = merchantRateStatus;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}
