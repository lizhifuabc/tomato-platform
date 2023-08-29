package com.tomato.reconciliation.task.internal.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.reconciliation.task.internal.domain.Task;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对账任务
 *
 * @author lizhifu
 * @since 2023/5/27
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task, Long> {

}
