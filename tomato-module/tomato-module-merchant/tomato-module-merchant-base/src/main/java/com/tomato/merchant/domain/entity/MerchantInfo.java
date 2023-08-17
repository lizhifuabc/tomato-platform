package com.tomato.merchant.domain.entity;

import com.tomato.jpa.domain.entity.BaseEntity;
import com.tomato.module.common.enums.MerchantType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;


/**
 * 商户信息
 *
 * @author lizhifu
 * @since  2022/11/25
 */
@Getter
@Setter
@Entity
@Table(name = "t_merchant_info",indexes = {@Index(name = "index_phone_search",columnList = "phoneSearch")})
public class MerchantInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("主键")
    private Long id;

    /**
     * 商户号
     */
    @Column(length = 64,nullable = false,unique = true)
    @Comment("商户号")
    private String merchantNo;
    /**
     * 商户类型
     */
    @Column(length = 64,nullable = false)
    @Comment("商户类型")
    // @Enumerated(EnumType.STRING)
    private String merchantType;
    /**
     * 商户名称
     */
    @Column(length = 64,nullable = false)
    @Comment("商户名称")
    private String merchantName;

    /**
     * 商户简称
     */
    @Column(length = 32,nullable = false)
    @Comment("商户简称")
    private String merchantShortName;
    /**
     * 手机号
     */
    @Column(length = 36,nullable = false,unique = true)
    @Comment("手机号")
    private String phone;
    /**
     * 手机号后四位模糊搜素
     */
    @Column(length = 36,nullable = false)
    @Comment("手机号后四位模糊搜素")
    private String phoneSearch;
    /**
     * 邮箱
     */
    @Column(length = 50,nullable = false,unique = true)
    @Comment("邮箱")
    private String email;
}
