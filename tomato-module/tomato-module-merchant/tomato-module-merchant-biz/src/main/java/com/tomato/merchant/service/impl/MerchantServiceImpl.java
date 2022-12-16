package com.tomato.merchant.service.impl;

import com.tomato.merchant.dao.MerchantInfoDao;
import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.domain.req.MerchantCreateReq;
import com.tomato.merchant.manager.MerchantSecurityManager;
import com.tomato.merchant.service.MerchantService;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.stereotype.Service;

/**
 * 商户信息
 *
 * @author lizhifu
 * @date 2022/11/25
 */
@Service
public class MerchantServiceImpl implements MerchantService {
    private final MerchantInfoDao merchantInfoDao;
    private final MerchantSecurityManager merchantSecurityManager;
    private final MySQLMaxValueIncrementer merchantIncrementer;
    public MerchantServiceImpl(MerchantInfoDao merchantInfoDao, MerchantSecurityManager merchantSecurityManager, MySQLMaxValueIncrementer merchantIncrementer) {
        this.merchantInfoDao = merchantInfoDao;
        this.merchantSecurityManager = merchantSecurityManager;
        this.merchantIncrementer = merchantIncrementer;
    }

    @Override
    public MerchantInfo selectByMerchantNo(String merchantNo) {
        // 查询商户信息
        MerchantInfo query = new MerchantInfo();
        query.setMerchantNo(merchantNo);
        Example<MerchantInfo> example = Example.of(query);
        return merchantInfoDao.findOne(example).orElse(null);
    }

    @Override
    public void createMerchant(MerchantCreateReq merchantCreateReq) {
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantNo(merchantIncrementer.nextStringValue());
        merchantInfo.setMerchantName(merchantCreateReq.getMerchantName());
        merchantInfo.setMerchantShortName(merchantCreateReq.getMerchantShortName());
        merchantInfo.setPhone(merchantSecurityManager.security(merchantCreateReq.getPhone()));
        merchantInfo.setPhoneSearch(merchantSecurityManager.phone(merchantCreateReq.getPhone()));
        merchantInfo.setEmail(merchantCreateReq.getEmail());
        merchantInfoDao.save(merchantInfo);
    }
}
