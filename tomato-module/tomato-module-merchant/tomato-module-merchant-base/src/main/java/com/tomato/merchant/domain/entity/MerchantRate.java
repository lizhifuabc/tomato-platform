package com.tomato.merchant.domain.entity;

import com.tomato.jpa.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户费率表
 * TODO 商户费率通道关联
 *
 * @author lizhifu
 * @date 2022/11/25
 */
@Entity
@Table(name = "t_merchant_rate",uniqueConstraints = {
        @UniqueConstraint(columnNames={"merchantNo", "payType"})
})
public class MerchantRate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 商户编号
     */
    @Column(length = 64,nullable = false)
    private String merchantNo;

    /**
     * 费率
     */
    @Column(nullable = false,precision = 20, scale = 6)
    private BigDecimal rate;

    /**
     * 状态: 0-停用, 1-正常
     */
    @Column(nullable = false)
    private Integer merchantRateStatus;

    /**
     * 支付方式
     */
    @Column(nullable = false)
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
