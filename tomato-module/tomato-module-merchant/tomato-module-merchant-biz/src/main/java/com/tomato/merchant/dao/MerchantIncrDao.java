package com.tomato.merchant.dao;

import com.tomato.merchant.domain.entity.MerchantIncr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 序列号生成
 *
 * @author lizhifu
 * @since 2022/12/15
 */
@Repository
public interface MerchantIncrDao extends JpaRepository<MerchantIncr,Long> {
}
