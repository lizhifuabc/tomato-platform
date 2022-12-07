package com.tomato.merchant.dao;

import com.tomato.merchant.domain.entity.MerchantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 商户信息
 *
 * @author lizhifu
 * @date 2022/11/25
 */
@Repository
public interface MerchantInfoDao extends JpaRepository<MerchantInfo,Long> {
}
