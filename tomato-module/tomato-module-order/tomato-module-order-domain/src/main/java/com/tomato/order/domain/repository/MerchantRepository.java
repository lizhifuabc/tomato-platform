package com.tomato.order.domain.repository;

import com.tomato.order.domain.domain.entity.MerchantEntity;

/**
 * 商户相关
 *
 * @author lizhifu
 * @since 2023/8/1
 */
public interface MerchantRepository {
    /**
     * 商户信息
     * @param merchantEntity 商户信息
     * @return 商户信息
     */
    MerchantEntity merchant(MerchantEntity merchantEntity);
    /**
     * 商户key
     * @param merchantNo 商户号
     * @return 商户key
     */
    String merchantKey(String merchantNo);
}
