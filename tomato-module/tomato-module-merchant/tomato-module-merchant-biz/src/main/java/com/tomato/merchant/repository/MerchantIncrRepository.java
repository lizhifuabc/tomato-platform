package com.tomato.merchant.repository;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.merchant.domain.entity.MerchantIncr;
import org.springframework.stereotype.Repository;

/**
 * 序列号生成
 *
 * @author lizhifu
 * @since 2022/12/15
 */
public interface MerchantIncrRepository extends BaseJpaRepository<MerchantIncr,Long> {
}
