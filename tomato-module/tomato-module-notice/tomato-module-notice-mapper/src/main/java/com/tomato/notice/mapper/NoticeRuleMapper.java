package com.tomato.notice.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.notice.entity.NoticeRuleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 通知规则
 *
 * @author lizhifu
 * @since 2023/4/24
 */
@Mapper
public interface NoticeRuleMapper extends BaseMapper<NoticeRuleEntity, Long> {

	/**
	 * 根据规则编码查询
	 * @param ruleCode 规则编码
	 * @return NoticeRuleEntity
	 */
	public NoticeRuleEntity selectByRuleCode(@Param("ruleCode") String ruleCode);

}
