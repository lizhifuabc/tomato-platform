package com.tomato.channel.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * MerchantRouterService
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@SpringBootTest
public class MerchantRouterServiceTest {
	@Resource
	private MerchantRouterService merchantRouterService;

	@Test
	public void route(){
		String merchantNo = "10202307240001001";
		String payType = "WX";
		merchantRouterService.route(merchantNo, payType);
	}
}
