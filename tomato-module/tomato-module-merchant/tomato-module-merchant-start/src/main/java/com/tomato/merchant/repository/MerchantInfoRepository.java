package com.tomato.merchant.repository;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.merchant.domain.entity.MerchantInfo;

import java.util.Optional;

/**
 * 商户信息
 *
 * @author lizhifu
 * @since  2022/11/25
 */
public interface MerchantInfoRepository extends BaseJpaRepository<MerchantInfo,Long> {
    /**
     * 根据手机号查询
     * @param phone 手机号
     * @return 商户信息
     */
    Optional<MerchantInfo> findByPhone(String phone);
    /**
     * 根据邮箱查询
     * @param email 邮箱
     * @return 商户信息
     */
    Optional<MerchantInfo> findByEmail(String email);

    /**
     * 根据商户号查询
     * @param merchantNo 商户号
     * @return 商户信息
     */
    Optional<MerchantInfo> findByMerchantNo(String merchantNo);
}
