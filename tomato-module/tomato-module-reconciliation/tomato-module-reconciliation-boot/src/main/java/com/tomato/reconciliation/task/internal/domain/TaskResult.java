package com.tomato.reconciliation.task.internal.domain;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 对账任务执行结果
 *
 * @author lizhifu
 * @since 2023/5/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskResult extends BaseEntity {
    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * 字段标识值
     */
    private String taskSignValue;
    /**
     * 数据明细值
     */
    private String taskValue;
    /**
     * 单边类型
     */
    private String unilateralType;
    /**
     * 对账日期
     */
    private LocalDate taskDate;
}
