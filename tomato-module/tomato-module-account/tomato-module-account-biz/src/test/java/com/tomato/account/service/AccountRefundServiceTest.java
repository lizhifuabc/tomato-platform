package com.tomato.account.service;

import com.tomato.account.domain.bo.AccountRefundBO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * 退款
 *
 * @author lizhifu
 * @since 2023/1/16
 */
@SpringBootTest
public class AccountRefundServiceTest {
    @Resource
    AccountRefundService accountRefundService;

    @Test
    public void test(){
        AccountRefundBO accountRefundBO = new AccountRefundBO();
        accountRefundBO.setMerchantNo("10202301010004121");
        accountRefundBO.setOrgThirdNo("f8ef1d32-1fa4-4ec0-a72e-9d38206de172");
        accountRefundService.refund(accountRefundBO);
    }
}
