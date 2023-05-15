package com.tomato.notice.controller;

import com.tomato.common.resp.Resp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 通知监控
 * {@link reactor.core.scheduler.BoundedElasticScheduler}
 * @author lizhifu
 * @since 2023/5/15
 */
@RestController
public class NoticeMonitorController {

    /**
     * 线程池统计信息
     * @return Resp
     */
    @RequestMapping("/notice/threadPoolStats")
    public Resp<Void> getThreadPoolStats(){
        Scheduler scheduler = Schedulers.boundedElastic();
        Disposable disposable = scheduler.schedule(() -> {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) scheduler;
            System.out.println("线程池中线程数目："+threadPoolExecutor.getPoolSize()+"，队列中等待执行的任务数目："+
                    threadPoolExecutor.getQueue().size()+"，已执行玩别的任务数目："+threadPoolExecutor.getCompletedTaskCount());
        }, 0, TimeUnit.SECONDS);
        return Resp.buildSuccess();
    }
}
