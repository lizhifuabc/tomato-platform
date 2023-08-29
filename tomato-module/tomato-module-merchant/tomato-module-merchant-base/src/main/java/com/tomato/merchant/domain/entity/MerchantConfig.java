package com.tomato.merchant.domain.entity;

import com.tomato.jpa.domain.entity.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

/**
 * 商户交易配置
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@Entity
@Table(name = "t_merchant_config", uniqueConstraints = { @UniqueConstraint(columnNames = { "merchant_no" }) })
public class MerchantConfig extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@Comment("主键")
	private Long id;

	/**
	 * 商户编号
	 */
	@Column(length = 64, nullable = false, name = "merchant_no")
	@Comment("商户编号")
	private String merchantNo;

	/**
	 * 商户密钥
	 */
	@Column(length = 64, nullable = false, name = "merchant_key")
	@Comment("商户密钥")
	private String merchantKey;

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

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

}
