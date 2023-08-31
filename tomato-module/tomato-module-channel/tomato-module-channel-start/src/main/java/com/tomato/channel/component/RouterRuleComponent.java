package com.tomato.channel.component;

import com.tomato.channel.domain.RouterRule;
import com.tomato.channel.mapper.RouterRuleMapper;
import com.tomato.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 路由规则表
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Component
@Slf4j
public class RouterRuleComponent {
	private final RouterRuleMapper routerRuleMapper;
    public RouterRuleComponent(RouterRuleMapper routerRuleMapper) {
        this.routerRuleMapper = routerRuleMapper;
    }

	public RouterRule filterRouterRule(String ruleNo){
        return routerRuleMapper.selectByRuleNo(ruleNo)
				.orElseThrow(() -> new BusinessException("路由规则不存在:"+ruleNo));
	}
}
