package com.tomato.order.infrastructure.repository.impl;

import com.tomato.common.exception.BusinessException;
import com.tomato.common.resp.Resp;
import com.tomato.merchant.api.RemoteMerchantService;
import com.tomato.merchant.domain.req.MerchantConfigQueryReq;
import com.tomato.merchant.domain.req.MerchantTradReq;
import com.tomato.merchant.domain.resp.MerchantTradResp;
import com.tomato.order.domain.domain.entity.MerchantEntity;
import com.tomato.order.domain.repository.MerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

/**
 * 商户相关
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@Repository
@Slf4j
public class MerchantRepositoryImpl implements MerchantRepository {
    private final RemoteMerchantService remoteMerchantService;

    public MerchantRepositoryImpl(RemoteMerchantService remoteMerchantService) {
        this.remoteMerchantService = remoteMerchantService;
    }

    @Override
    public MerchantEntity merchant(MerchantEntity merchantEntity) {
        MerchantTradReq merchantCreateReq = new MerchantTradReq();
        BeanUtils.copyProperties(merchantEntity,merchantCreateReq);
        log.info("请求远程服务 merchant 参数：merchantCreateReq:{}",merchantCreateReq);
        Resp<MerchantTradResp> trade = remoteMerchantService.trade(merchantCreateReq);
        if (trade.isSuccess()) {
            MerchantTradResp data = trade.getData();
            MerchantEntity re = new MerchantEntity();
            BeanUtils.copyProperties(data,re);
            return re;
        }
        throw new BusinessException(trade.getMsg());
    }

    @Override
    public String merchantKey(String merchantNo) {
        MerchantConfigQueryReq merchantConfigQueryReq = new MerchantConfigQueryReq();
        merchantConfigQueryReq.setMerchantNo(merchantNo);
        return remoteMerchantService.queryConfig(merchantConfigQueryReq).getData().getMerchantKey();
    }
}
