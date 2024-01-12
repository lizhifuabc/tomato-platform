package com.tomato.notice.entity;

import com.tomato.common.domain.entity.BaseEntity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知规则
 *
 * @author lizhifu
 * @since 2023/4/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_notice_rule")
public class NoticeRuleEntity extends BaseEntity {

}
