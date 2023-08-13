package com.tomato.merchant.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.merchant.domain.entity.MerchantConfig;
import com.tomato.merchant.domain.req.MerchantConfigQueryReq;
import com.tomato.merchant.domain.req.MerchantConfigReq;
import com.tomato.merchant.domain.resp.MerchantConfigQueryResp;
import com.tomato.merchant.repository.MerchantConfigRepository;
import com.tomato.merchant.repository.MerchantInfoRepository;
import com.tomato.web.core.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 商户配置
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@Service
public class MerchantConfigService {
    private final MerchantConfigRepository merchantConfigRepository;
    private final MerchantInfoRepository merchantInfoRepository;
    public MerchantConfigService(MerchantConfigRepository merchantConfigRepository, MerchantInfoRepository merchantInfoRepository) {
        this.merchantConfigRepository = merchantConfigRepository;
        this.merchantInfoRepository = merchantInfoRepository;
    }

    public void create(MerchantConfigReq merchantConfigReq) {
        merchantInfoRepository.findByMerchantNo(merchantConfigReq.getMerchantNo()).orElseThrow(()->new BusinessException("商户不存在"));
        MerchantConfig merchantConfig = BeanUtil.copy(merchantConfigReq, MerchantConfig.class);
        // TODO 生成商户密钥算法
        merchantConfig.setMerchantKey(UUID.randomUUID().toString());
        merchantConfigRepository.save(merchantConfig);
    }

    public MerchantConfigQueryResp query(MerchantConfigQueryReq merchantConfigReq) {
        MerchantConfig merchantConfig = merchantConfigRepository.findByMerchantNo(merchantConfigReq.getMerchantNo()).orElseThrow(()->new BusinessException("商户不存在"));
        return BeanUtil.copy(merchantConfig,MerchantConfigQueryResp.class);
    }
}
