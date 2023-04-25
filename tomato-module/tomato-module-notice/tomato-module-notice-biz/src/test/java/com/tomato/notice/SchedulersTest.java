package com.tomato.notice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.Disposable;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Scheduler
 *
 * @author lizhifu
 * @since 2023/4/25
 */
@SpringBootApplication
public class SchedulersTest {
    @Test
    public void test(){
        // Schedulers.parallel(): 适合处理CPU密集型任务，比如并行处理数据集，计算等任务，适合于多核处理器。
        // Schedulers.immediate(): 不会对当前线程进行任何处理，直接返回响应，适用于简单，轻量级的任务。
        // Schedulers.single(): 适合于长时间执行的阻塞任务，比如I/O等待，读取文件，处理长时间的HTTP请求等，只会使用一个线程。
        // Schedulers.boundedElastic(): 适合于需要执行异步任务，并且需要控制并发线程数的情况。它会使用一个弹性线程池，
        // 可以根据当前系统资源的情况自动调整线程数，防止线程池耗尽。这种调度器适用于执行网络I/O或者数据库I/O等的场景。
        System.out.println("Schedulers.boundedElastic()默认参数");
        // 用于指定默认的“boundedElastic”调度器队列大小。该常量的默认值为100000。
        System.out.println(Schedulers.DEFAULT_BOUNDED_ELASTIC_QUEUESIZE);
        // 默认的“boundedElastic”调度器的线程池大小。该常量的默认值为10*Runtime.getRuntime().availableProcessors()。
        System.out.println(Schedulers.DEFAULT_BOUNDED_ELASTIC_SIZE);
        // 默认的调度器线程池的大小。该常量的默认值为 Runtime.getRuntime().availableProcessors()，即可用处理器的数量。
        System.out.println(Schedulers.DEFAULT_POOL_SIZE);


        // Schedulers.boundedElastic()
        // 有界的弹性线程池,它会回收闲置的线程，默认是60s;它对创建的线程数做了限制，默认值为CPU内核数x 10，
        // 达到上限后，最多可提交10万个任务；
        Scheduler scheduler = Schedulers.boundedElastic();

        Scheduler scheduler1 = Schedulers.newBoundedElastic(10, 100, "test");
        scheduler.createWorker().schedule(() -> {
            throw new RuntimeException("boundedElastic createWorker test");
        });

        scheduler.schedule(() -> {
            throw new RuntimeException("boundedElastic test");
        });

        String key = "test";
        Schedulers.onHandleError(key,(throwable, o) -> {
            System.out.println("onHandleError");
        });

    }
}
