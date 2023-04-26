package com.tomato.merchant.service.impl;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.jpa.domain.service.AbstractService;
import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.domain.req.MerchantCreateReq;
import com.tomato.merchant.manager.MerchantSecurityManager;
import com.tomato.merchant.repository.MerchantInfoRepository;
import com.tomato.merchant.service.MerchantNoService;
import com.tomato.merchant.service.MerchantService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * 商户信息
 *
 * @author lizhifu
 * @since  2022/11/25
 */
@Service
public class MerchantServiceImpl implements MerchantService {
    private final MerchantInfoRepository merchantInfoRepository;
    private final MerchantSecurityManager merchantSecurityManager;
    private final MerchantNoService merchantNoService;
    public MerchantServiceImpl(MerchantInfoRepository merchantInfoRepository, MerchantSecurityManager merchantSecurityManager, MerchantNoService merchantNoService) {
        this.merchantInfoRepository = merchantInfoRepository;
        this.merchantSecurityManager = merchantSecurityManager;
        this.merchantNoService = merchantNoService;
    }

    @Override
    public MerchantInfo selectByMerchantNo(String merchantNo) {
        // 查询商户信息
        MerchantInfo query = new MerchantInfo();
        query.setMerchantNo(merchantNo);
        Example<MerchantInfo> example = Example.of(query);
        return merchantInfoRepository.findOne(example).orElse(null);
    }

    @Override
    public void createMerchant(MerchantCreateReq merchantCreateReq) {
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantNo(merchantNoService.nextStringValue());
        merchantInfo.setMerchantName(merchantCreateReq.getMerchantName());
        merchantInfo.setMerchantShortName(merchantCreateReq.getMerchantShortName());
        merchantInfo.setPhone(merchantSecurityManager.security(merchantCreateReq.getPhone()));
        merchantInfo.setPhoneSearch(merchantSecurityManager.phone(merchantCreateReq.getPhone()));
        merchantInfo.setEmail(merchantCreateReq.getEmail());
        merchantInfoRepository.save(merchantInfo);
    }
}
