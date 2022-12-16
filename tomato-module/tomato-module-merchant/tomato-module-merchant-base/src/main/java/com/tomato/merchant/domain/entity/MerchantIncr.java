package com.tomato.merchant.domain.entity;

import com.tomato.jpa.domain.entity.JpaBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 序列号生成
 *
 * @author lizhifu
 * @since 2022/12/15
 */
@Entity
@Table(name = "t_merchant_incr")
@Data
public class MerchantIncr extends JpaBaseEntity {
    /**
     * 商户号
     */
    @Column
    private Long merchantNo;
}
