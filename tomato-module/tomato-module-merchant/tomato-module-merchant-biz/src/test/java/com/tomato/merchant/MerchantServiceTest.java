package com.tomato.merchant;

import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.domain.req.MerchantCreateReq;
import com.tomato.merchant.service.MerchantService;
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
public class MerchantServiceTest {
    @Resource
    MerchantService merchantService;

    @Test
    public void test(){
        MerchantCreateReq merchantCreateReq = new MerchantCreateReq();
        merchantCreateReq.setMerchantName("李志福测试");
        merchantCreateReq.setMerchantShortName("李志福");
        merchantCreateReq.setPhone("18888888888");
        merchantCreateReq.setEmail("test@163.com");
        merchantService.createMerchant(merchantCreateReq);
        MerchantInfo merchantInfo = merchantService.selectByMerchantNo("12");
        System.out.println(merchantInfo);
    }
}
