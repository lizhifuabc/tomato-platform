package com.tomato.channel.component;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * MerchantRouterRuleComponent
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@SpringBootTest
public class MerchantRouterRuleComponentTest {
	@Resource
	MerchantRouterRuleComponent merchantRouterRuleComponent;

	@Test
	public void test(){
		merchantRouterRuleComponent.createMerchantRouterRule(
				"10020219",
				"DEFAULT",
				"WX");
	}
}
