package com.tomato.merchant;

import com.tomato.merchant.service.MerchantNoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * MerchantNoService
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@SpringBootTest
public class MerchantNoServiceTest {
    @Resource
    MerchantNoService merchantNoService;

    @Test
    public void test(){
        System.out.println(merchantNoService.nextStringValue());
    }
}
