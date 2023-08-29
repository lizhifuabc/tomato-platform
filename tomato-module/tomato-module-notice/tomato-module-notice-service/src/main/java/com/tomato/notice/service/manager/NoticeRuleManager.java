package com.tomato.notice.service.manager;

import com.tomato.notice.entity.NoticeRuleEntity;
import com.tomato.notice.mapper.NoticeRuleMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 通知规则
 *
 * @author lizhifu
 * @since 2023/4/25
 */
@Service
public class NoticeRuleManager {

	private final NoticeRuleMapper noticeRuleMapper;

	public NoticeRuleManager(NoticeRuleMapper noticeRuleMapper) {
		this.noticeRuleMapper = noticeRuleMapper;
	}

	public Optional<NoticeRuleEntity> selectByRuleCode(String ruleCode) {
		return Optional.ofNullable(noticeRuleMapper.selectByRuleCode(ruleCode));
	}

}
