package com.tomato.merchant.domain.entity;

import com.tomato.jpa.domain.entity.JpaBaseEntity;
import jakarta.persistence.*;
import lombok.Data;

/**
 * 商户信息
 *
 * @author lizhifu
 * @date 2022/11/25
 */
@Entity
@Table(name = "t_merchant_info")
@Data
public class MerchantInfo extends JpaBaseEntity {
    /**
     * 商户号
     */
    @Column(length = 64,nullable = false,unique = true)
    private String merchantNo;

    /**
     * 商户名称
     */
    @Column(length = 64,nullable = false)
    private String merchantName;

    /**
     * 商户简称
     */
    @Column(length = 32,nullable = false)
    private String merchantShortName;
    /**
     * 商户状态: 0-停用, 1-正常
     */
    @Column(nullable = false)
    private Byte merchantStatus;
}
