package com.tomato.account.service;

import com.tomato.account.domain.bo.AccountRefundBO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        accountRefundBO.setOrgThirdNo("b096f5e3-a5c9-44e3-a299-70b50bbf34e7");
        accountRefundService.settleRefund(accountRefundBO);
    }
}
