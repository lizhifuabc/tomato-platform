package com.tomato.channel.mapper;

import com.tomato.channel.domain.MerchantRouterRule;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * MerchantRouterRuleMapperTest
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@SpringBootTest
public class MerchantRouterRuleMapperTest {
	@Resource
	MerchantRouterRuleMapper merchantRouterRuleMapper;

	@Test
	public void selectByMerchant() {
		List<MerchantRouterRule> merchantRouterRules =
				merchantRouterRuleMapper.selectByMerchant("10202307240001001", "WX");
		System.out.println(merchantRouterRules);
	}
}
