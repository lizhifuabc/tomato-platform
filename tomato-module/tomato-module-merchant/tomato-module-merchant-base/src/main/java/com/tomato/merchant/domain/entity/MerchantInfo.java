package com.tomato.merchant.domain.entity;

import com.tomato.jpa.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商户信息
 *
 * @author lizhifu
 * @since  2022/11/25
 */
@Entity
@Table(name = "t_merchant_info",indexes = {@Index(name = "index_phone_search",columnList = "phoneSearch")})
public class MerchantInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantShortName() {
        return merchantShortName;
    }

    public void setMerchantShortName(String merchantShortName) {
        this.merchantShortName = merchantShortName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneSearch() {
        return phoneSearch;
    }

    public void setPhoneSearch(String phoneSearch) {
        this.phoneSearch = phoneSearch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(Integer merchantStatus) {
        this.merchantStatus = merchantStatus;
    }
}
