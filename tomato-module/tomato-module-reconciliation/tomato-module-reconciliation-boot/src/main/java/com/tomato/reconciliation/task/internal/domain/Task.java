package com.tomato.reconciliation.task.internal.domain;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对账任务
 *
 * @author lizhifu
 * @since 2023/5/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends BaseEntity {
    private String taskName;
    private String taskDesc;

    private String taskSign;

    private String upTableSql;
    private Long upDbInfoId;

    private String downTableSql;
    private Long downDbInfoId;
}
