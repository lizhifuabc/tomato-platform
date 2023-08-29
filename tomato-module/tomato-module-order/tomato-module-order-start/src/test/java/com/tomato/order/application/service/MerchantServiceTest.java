package com.tomato.order.application.service;

import com.tomato.order.domain.domain.entity.MerchantEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * MerchantService
 *
 * @author lizhifu
 * @since 2023/8/2
 */
@SpringBootTest
public class MerchantServiceTest {

	@Resource
	MerchantService merchantService;

	@Test
	public void test() {
		MerchantEntity merchantEntity = new MerchantEntity();
		merchantEntity.setMerchantNo("10202307240001001");
		merchantEntity.setPayType(1);
		merchantService.merchant(merchantEntity);
	}

}
