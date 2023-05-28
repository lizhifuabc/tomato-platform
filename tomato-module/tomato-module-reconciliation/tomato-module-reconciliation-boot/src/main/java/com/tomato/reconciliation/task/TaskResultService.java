package com.tomato.reconciliation.task;

import com.tomato.reconciliation.enums.UnilateralType;
import com.tomato.reconciliation.support.ReconciliationSupport;
import com.tomato.reconciliation.task.internal.domain.Task;
import com.tomato.reconciliation.task.internal.domain.TaskResult;
import com.tomato.reconciliation.task.internal.mapper.TaskResultMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TaskResult
 *
 * @author lizhifu
 * @since 2023/5/28
 */
@Service
@Slf4j
public class TaskResultService {
    @Resource
    private TaskResultMapper taskResultMapper;
    @Transactional(rollbackFor = Exception.class)
    public void result(ReconciliationSupport reconciliationSupport, LocalDate taskDate){
        Task task = reconciliationSupport.getTask();
        Map<String, Map<String, Object>> downMap = reconciliationSupport.getDownMap();
        Map<String, Map<String, Object>> upMap = reconciliationSupport.getUpMap();

        // 循环 upMap
        Set<String> upSet = upMap.keySet();
        List<TaskResult> upList = new ArrayList<>();
        for (String upKey : upSet) {
            TaskResult taskResult = new TaskResult();
            taskResult.setTaskId(task.getId());
            taskResult.setTaskSignValue(upKey);
            taskResult.setTaskValue(upMap.get(upKey).toString());
            taskResult.setUnilateralType(UnilateralType.UP_ERROR.getValue());
            taskResult.setTaskDate(taskDate);
            upList.add(taskResult);
        }
        // 循环 downMap
        Set<String> downSet = downMap.keySet();
        List<TaskResult> downList = new ArrayList<>();
        for (String downKey : downSet) {
            TaskResult taskResult = new TaskResult();
            taskResult.setTaskId(task.getId());
            taskResult.setTaskSignValue(downKey);
            taskResult.setTaskValue(downMap.get(downKey).toString());
            taskResult.setUnilateralType(UnilateralType.DOWN_ERROR.getValue());
            taskResult.setTaskDate(taskDate);
            downList.add(taskResult);
        }
        // TODO 事务大小优化，list 构建提出事务
        TaskResult delete = new TaskResult();
        delete.setTaskId(task.getId());
        delete.setTaskDate(taskDate);
        taskResultMapper.deleteByCriteria(delete);
        taskResultMapper.batchInsertSelective(upList);
        taskResultMapper.batchInsertSelective(downList);
    }
}
