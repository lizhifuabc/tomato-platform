# 线程池监控



## 线程池参数

- corePoolSize：一般来说，它应该设置为处理当前负载的最大线程数。如果线程数太少，可能会导致请求排队，降低响应速度；如果线程数太多，可能会消耗过多的系统资源。

- maximumPoolSize：最大线程数应该设置为系统能够支持的最大线程数，通常不宜过大。这可以避免系统因线程数过多而导致的性能下降和资源浪费。

- keepAliveTime：该参数设置空闲线程的最长存活时间。如果线程池中的线程超过了corePoolSize，且处于空闲状态的时间超过了keepAliveTime，这些线程将被终止。这个时间需要根据应用程序的负载和硬件资源进行调整。如果keepAliveTime设置太短，可能会导致线程频繁创建和销毁，影响性能；如果设置太长，可能会消耗过多的系统资源。

- workQueue：工作队列用于存储等待执行的任务。应该根据应用程序的负载和硬件资源选择适当的队列类型，比如ArrayBlockingQueue或LinkedBlockingQueue。如果队列长度太小，可能会导致请求排队，降低响应速度；如果队列长度太大，可能会消耗过多的系统资源。

- rejectedExecutionHandler：拒绝策略用于处理当工作队列已满，无法接受新任务时的情况。可以选择一些预定义的策略，比如AbortPolicy、CallerRunsPolicy、DiscardOldestPolicy或DiscardPolicy。需要根据实际情况选择最合适的拒绝策略，以避免任务丢失或长时间阻塞。