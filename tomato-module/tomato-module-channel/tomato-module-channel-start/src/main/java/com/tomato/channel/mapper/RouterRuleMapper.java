package com.tomato.channel.mapper;

import com.tomato.channel.domain.RouterRule;
import com.tomato.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * 路由规则表
 *
 * @author lizhifu
 * @since 2023/8/31
 */
@Mapper
public interface RouterRuleMapper extends BaseMapper<RouterRule,Long> {

	/**
	 * 查询路由规则
	 * @param ruleNo 规则编号
	 * @return 路由规则
	 */
	Optional<RouterRule> selectByRuleNo(@Param("ruleNo") String ruleNo);
}
