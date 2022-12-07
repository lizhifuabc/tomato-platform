package com.tomato.merchant.service.impl;

import com.tomato.merchant.dao.MerchantInfoDao;
import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.service.MerchantService;
import org.springframework.data.domain.Example;
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

    public MerchantServiceImpl(MerchantInfoDao merchantInfoDao) {
        this.merchantInfoDao = merchantInfoDao;
    }

    @Override
    public MerchantInfo selectByMerchantNo(String merchantNo) {
        // 查询商户信息
        MerchantInfo query = new MerchantInfo();
        query.setMerchantNo(merchantNo);
        Example<MerchantInfo> example = Example.of(query);
        return merchantInfoDao.findOne(example).orElse(null);
    }
}
