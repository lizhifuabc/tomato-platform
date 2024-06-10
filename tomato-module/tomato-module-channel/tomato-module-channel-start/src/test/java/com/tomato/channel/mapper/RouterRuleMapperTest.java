package com.tomato.channel.mapper;

import com.tomato.channel.domain.RouterRule;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * RouterRuleMapper
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@SpringBootTest
public class RouterRuleMapperTest {
	@Resource
	private RouterRuleMapper routerRuleMapper;

	@Test
	public void test(){
		RouterRule routerRule = new RouterRule();
		routerRule.setRuleDescription("测试路由");
		routerRule.setRuleName("测试路由");
		routerRule.setRuleNo("10202307240001001_WX_BACKUP");
		routerRule.setPayType("WX");
//		routerRuleMapper.insertSelective(routerRule);
	}
}
