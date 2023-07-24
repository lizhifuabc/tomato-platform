package com.tomato.merchant.domain.entity;

import com.tomato.jpa.domain.entity.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @Comment("主键")
    private Long id;

    /**
     * 商户编号
     */
    @Column(length = 64,nullable = false,name = "merchant_no")
    @Comment("商户编号")
    private String merchantNo;

    /**
     * 交易费率
     */
    @Column(nullable = false,precision = 14, scale = 4)
    @Comment("交易费率")
    private BigDecimal tradeRate;

    /**
     * 分账费率
     */
    @Column(nullable = true,precision = 14, scale = 4)
    @Comment("分账费率")
    private BigDecimal splitRate;

    /**
     * 将来交易费率
     */
    @Column(nullable = true,precision = 14, scale = 4)
    @Comment("将来交易费率")
    private BigDecimal futureTradeRate;

    /**
     * 将来分账费率
     */
    @Column(nullable = true,precision = 14, scale = 4)
    @Comment("将来分账费率")
    private BigDecimal futureSplitRate;
    /**
     * 将来分账费率生效时间
     */
    @Column
    @Comment("将来分账费率生效时间")
    private LocalDateTime splitRateEffectiveTime;
    /**
     * 将来交易费率生效时间
     */
    @Column
    @Comment("将来交易费率生效时间")
    private LocalDateTime tradeRateEffectiveTime;

    /**
     * 交易是否停用: 0-否, 1-是
     */
    @Column(nullable = false,columnDefinition = "tinyint(0) not null default 0",insertable = false)
    @Comment("交易是否停用: 0-否, 1-是")
    private Integer tradeStatus;
    /**
     * 分账是否停用: 0-否, 1-是
     */
    @Column(nullable = false,columnDefinition = "tinyint(0) not null default 0",insertable = false)
    @Comment("分账是否停用: 0-否, 1-是")
    private Integer splitStatus;

    /**
     * 支付方式
     */
    @Column(nullable = false,name = "pay_type")
    @Comment("支付方式")
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

    public BigDecimal getTradeRate() {
        return tradeRate;
    }

    public void setTradeRate(BigDecimal tradeRate) {
        this.tradeRate = tradeRate;
    }

    public BigDecimal getSplitRate() {
        return splitRate;
    }

    public void setSplitRate(BigDecimal splitRate) {
        this.splitRate = splitRate;
    }

    public BigDecimal getFutureTradeRate() {
        return futureTradeRate;
    }

    public void setFutureTradeRate(BigDecimal futureTradeRate) {
        this.futureTradeRate = futureTradeRate;
    }

    public BigDecimal getFutureSplitRate() {
        return futureSplitRate;
    }

    public void setFutureSplitRate(BigDecimal futureSplitRate) {
        this.futureSplitRate = futureSplitRate;
    }

    public LocalDateTime getSplitRateEffectiveTime() {
        return splitRateEffectiveTime;
    }

    public void setSplitRateEffectiveTime(LocalDateTime splitRateEffectiveTime) {
        this.splitRateEffectiveTime = splitRateEffectiveTime;
    }

    public LocalDateTime getTradeRateEffectiveTime() {
        return tradeRateEffectiveTime;
    }

    public void setTradeRateEffectiveTime(LocalDateTime tradeRateEffectiveTime) {
        this.tradeRateEffectiveTime = tradeRateEffectiveTime;
    }
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Integer getSplitStatus() {
        return splitStatus;
    }

    public void setSplitStatus(Integer splitStatus) {
        this.splitStatus = splitStatus;
    }
}
