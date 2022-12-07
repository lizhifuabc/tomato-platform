package com.tomato.merchant.domain.entity;

import com.tomato.jpa.domain.entity.JpaBaseEntity;
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
@Data
@Entity
@Table(name = "t_merchant_rate",uniqueConstraints = {
        @UniqueConstraint(columnNames={"merchantNo", "payType"})
})
public class MerchantRate extends JpaBaseEntity {
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
    private Byte merchantRateStatus;

    /**
     * 支付方式
     */
    @Column(nullable = false)
    private Byte payType;
}
