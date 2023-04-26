package com.tomato.merchant.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.jpa.domain.service.AbstractService;
import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.manager.MerchantSecurityManager;
import com.tomato.merchant.repository.MerchantInfoRepository;
import org.springframework.stereotype.Service;

/**
 * 商户信息
 *
 * @author lizhifu
 * @since 2023/4/26
 */
@Service
public class MerchantInfoService extends AbstractService<MerchantInfo,Long> {
    private final MerchantInfoRepository merchantInfoRepository;
    private final MerchantNoService merchantNoService;
    private final MerchantSecurityManager merchantSecurityManager;
    public MerchantInfoService(MerchantInfoRepository merchantInfoRepository, MerchantNoService merchantNoService, MerchantSecurityManager merchantSecurityManager) {
        this.merchantInfoRepository = merchantInfoRepository;
        this.merchantNoService = merchantNoService;
        this.merchantSecurityManager = merchantSecurityManager;
    }
    @Override
    public MerchantInfo save(MerchantInfo merchantInfo){
        merchantInfoRepository.findByEmail(merchantInfo.getEmail()).ifPresent(e->{
            throw new BusinessException("邮箱已存在");
        });
        merchantInfoRepository.findByPhone(merchantInfo.getPhone()).ifPresent(e->{
            throw new BusinessException("手机号已存在");
        });
        merchantInfo.setPhone(merchantSecurityManager.security(merchantInfo.getPhone()));
        merchantInfo.setPhoneSearch(merchantSecurityManager.phone(merchantInfo.getPhone()));
        merchantInfo.setMerchantNo(merchantNoService.nextStringValue());
        return merchantInfoRepository.save(merchantInfo);
    }
    @Override
    public BaseJpaRepository<MerchantInfo, Long> getRepository() {
        return this.merchantInfoRepository;
    }
}
