package com.tomato.reconciliation.task.internal.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.reconciliation.task.internal.domain.TaskResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对账任务执行结果
 *
 * @author lizhifu
 * @since 2023/5/28
 */
@Mapper
public interface TaskResultMapper extends BaseMapper<TaskResult, Long> {
}
