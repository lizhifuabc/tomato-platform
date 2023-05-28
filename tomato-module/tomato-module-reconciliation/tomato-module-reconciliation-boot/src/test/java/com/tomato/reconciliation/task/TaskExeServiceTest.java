package com.tomato.reconciliation.task;

import com.tomato.mybatis.domain.Sort;
import com.tomato.reconciliation.dbinfo.internal.mapper.DbInfoMapper;
import com.tomato.reconciliation.task.internal.domain.Task;
import com.tomato.reconciliation.task.internal.mapper.TaskMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

/**
 * TaskExeService
 *
 * @author lizhifu
 * @since 2023/5/28
 */
@SpringBootTest
public class TaskExeServiceTest {
    @Resource
    TaskExeService taskExeService;
    @Resource
    TaskMapper taskMapper;
    @Resource
    DbInfoMapper dbInfoMapper;
    @Test
    public void test(){
        Sort sort = Sort.by("id", Sort.Direction.ASC).and("create_time", Sort.Direction.DESC);
        List<Task> taskList = taskMapper.selectAll(sort);
        taskList.forEach(task -> {
            taskExeService.exe(task, LocalDate.now().minusDays(2));
        });
    }
}
