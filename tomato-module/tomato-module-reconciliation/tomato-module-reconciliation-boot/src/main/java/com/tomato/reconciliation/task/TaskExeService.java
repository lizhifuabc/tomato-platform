package com.tomato.reconciliation.task;

import com.tomato.reconciliation.dbinfo.internal.domain.DbInfo;
import com.tomato.reconciliation.dbinfo.internal.mapper.DbInfoMapper;
import com.tomato.reconciliation.support.ReconciliationSupport;
import com.tomato.reconciliation.task.internal.domain.Task;
import com.tomato.reconciliation.task.internal.mapper.TaskMapper;
import com.tomato.reconciliation.utils.ExecuteQueryUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 对账任务执行
 *
 * @author lizhifu
 * @since 2023/5/27
 */
@Service
@Slf4j
public class TaskExeService {
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private DbInfoMapper dbInfoMapper;
    @Resource
    private TaskResultService taskResultService;
    public void exe(Task task,LocalDate taskDate){
        log.info("执行对账任务:id:{},name:{}",task.getId(),task.getTaskName());
        Optional<DbInfo> upDbInfo = dbInfoMapper.selectByPrimaryKey(task.getUpDbInfoId());
        Optional<DbInfo> downDbInfo = dbInfoMapper.selectByPrimaryKey(task.getDownDbInfoId());

        List<Map<String, Object>> upList = ExecuteQueryUtil.query(upDbInfo.get(), TaskSqlAnalysis.analysis(task.getUpTableSql()));
        log.info("执行对账任务:上游:{}",upList.size());

        List<Map<String, Object>> downList = ExecuteQueryUtil.query(downDbInfo.get(), TaskSqlAnalysis.analysis(task.getDownTableSql()));
        log.info("执行对账任务:下游:{}",downList.size());

        ReconciliationSupport reconciliationSupport = new ReconciliationSupport(upList,downList,task);
        reconciliationSupport.reconciliation();

        taskResultService.result(reconciliationSupport, taskDate);
    }
}
