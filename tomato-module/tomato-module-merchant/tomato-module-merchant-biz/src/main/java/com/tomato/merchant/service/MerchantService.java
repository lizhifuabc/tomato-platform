package com.tomato.merchant.service;


import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.domain.req.MerchantCreateReq;

/**
 * 商户信息
 *
 * @author lizhifu
 * @date 2022/11/25
 */
public interface MerchantService {
    /**
     * 查询商户信息
     * @param merchantNo 商编
     * @return
     */
    public MerchantInfo selectByMerchantNo(String merchantNo);

    /**
     * 商户创建
     * @param merchantCreateReq
     */
    public void createMerchant(MerchantCreateReq merchantCreateReq);
}
