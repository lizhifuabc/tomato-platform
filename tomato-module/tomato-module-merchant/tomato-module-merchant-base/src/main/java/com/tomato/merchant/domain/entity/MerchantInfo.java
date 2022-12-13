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
@Table(name = "t_merchant_info",indexes = {@Index(name = "index_phone_search",columnList = "phoneSearch")})
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
     * 手机号
     */
    @Column(length = 36,nullable = false,unique = true)
    private String phone;
    /**
     * 手机号后四位模糊搜素
     */
    @Column(length = 36,nullable = false)
    private String phoneSearch;
    /**
     * 邮箱
     */
    @Column(length = 50,nullable = false,unique = true)
    private String email;
    /**
     * 是否停用: 0-否, 1-是
     */
    @Column(nullable = false,columnDefinition = "tinyint(0) not null default 0",insertable = false)
    private Integer merchantStatus;
}
