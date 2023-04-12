package com.tomato.merchant.dao;

import com.tomato.domain.core.enums.YesNoTypeEnum;
import com.tomato.merchant.domain.entity.MerchantInfo;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 商户信息
 *
 * @author lizhifu
 * @date 2022/11/25
 */
@SpringBootTest
public class MerchantInfoDaoTest {
    @Resource
    MerchantInfoDao merchantInfoDao;

    @Test
    public void test(){
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantNo("12");
        merchantInfo.setMerchantName("12");
        merchantInfo.setMerchantShortName("12");
        merchantInfo.setMerchantStatus(YesNoTypeEnum.YES.getValue());
        merchantInfo.setEmail("123");
        merchantInfo.setPhone("123");
        merchantInfoDao.save(merchantInfo);
    }

}
