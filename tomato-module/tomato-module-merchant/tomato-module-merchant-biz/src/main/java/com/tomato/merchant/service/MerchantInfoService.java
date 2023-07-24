package com.tomato.merchant.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.jpa.domain.service.AbstractService;
import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.domain.entity.MerchantRate;
import com.tomato.merchant.domain.req.MerchantCreateReq;
import com.tomato.merchant.manager.MerchantSecurityManager;
import com.tomato.merchant.repository.MerchantInfoRepository;
import com.tomato.merchant.repository.MerchantRateRepository;
import com.tomato.web.core.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商户信息
 *
 * @author lizhifu
 * @since 2023/4/26
 */
@Service
@Slf4j
public class MerchantInfoService extends AbstractService<MerchantInfo,Long> {
    private final MerchantRateRepository merchantRateRepository;
    private final MerchantInfoRepository merchantInfoRepository;
    private final MerchantNoService merchantNoService;
    private final MerchantSecurityManager merchantSecurityManager;
    public MerchantInfoService(MerchantInfoRepository merchantInfoRepository, MerchantNoService merchantNoService, MerchantSecurityManager merchantSecurityManager,
                               MerchantRateRepository merchantRateRepository) {
        this.merchantInfoRepository = merchantInfoRepository;
        this.merchantNoService = merchantNoService;
        this.merchantSecurityManager = merchantSecurityManager;
        this.merchantRateRepository = merchantRateRepository;
    }
    @Transactional(rollbackFor = Exception.class)
    public MerchantInfo save(MerchantCreateReq merchantCreateReq){
        merchantInfoRepository.findByEmail(merchantCreateReq.getEmail()).ifPresent(e->{
            throw new BusinessException("邮箱已存在");
        });
        merchantInfoRepository.findByPhone(merchantSecurityManager.security(merchantCreateReq.getPhone())).ifPresent(e->{
            throw new BusinessException("手机号已存在");
        });
        // 生成商户号
        String merchantNo = merchantNoService.nextStringValue();
        // 保存商户信息
        MerchantInfo merchantInfo = BeanUtil.copy(merchantCreateReq, MerchantInfo.class);
        merchantInfo.setPhone(merchantSecurityManager.security(merchantInfo.getPhone()));
        merchantInfo.setPhoneSearch(merchantSecurityManager.phone(merchantInfo.getPhone()));
        merchantInfo.setMerchantNo(merchantNo);
        merchantInfoRepository.save(merchantInfo);
        // 保存商户费率
        List<MerchantRate> merchantRateList = new ArrayList<>();
        merchantCreateReq.getMerchantRateList().forEach(e->{
            MerchantRate merchantRate = BeanUtil.copy(e, MerchantRate.class);
            merchantRate.setMerchantNo(merchantNo);
            merchantRateList.add(merchantRate);
        });
        merchantRateRepository.saveAll(merchantRateList);
        return merchantInfo;
    }
    @Override
    public BaseJpaRepository<MerchantInfo, Long> getRepository() {
        return this.merchantInfoRepository;
    }
}
