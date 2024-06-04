package com.tomato.dynamic.thread.enums;

import com.tomato.dynamic.thread.exception.DynamicThreadException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * 阻塞队列类型
 *
 * @author lizhifu
 * @since 2024/5/22
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum QueueTypeEnum {

	/**
	 * 创建队列时，指定队列的最大容量
	 * 若有新的任务要执行，如果线程池中的线程数小于corePoolSize，则会优先创建新的线程。若大于corePoolSize，则会将新任务加入到等待队列中。
	 * 若等待队列已满，且线程数小于maximumPoolSize，则会创建新的线程执行任务。
	 */
	ARRAY_BLOCKING_QUEUE(1, "ArrayBlockingQueue"),
	/**
	 * 无界的任务队列（LinkedBlockingQueue）
	 * 当不指定队列大小时，LinkedBlockingQueue为无界的任务队列。
	 * 与有界队列相比，除非系统资源耗尽，否则不存在任务入队失败的情况。
	 * 若有新的任务要执行，如果线程池中的线程数小于corePoolSize，线程池会创建新的线程。若大于corePoolSize，此时又没有空闲的线程资源，则任务直接进入等待队列。
	 * 当线程池中的线程数达到corePoolSize后，线程池不会创建新的线程。
	 * 若任务创建和处理的速度差异很大，无界队列将保持快速增长，直到耗尽系统内存。
	 */
	LINKED_BLOCKING_QUEUE(2, "LinkedBlockingQueue"),
	/**
	 * 优先任务队列（PriorityBlockingQueue）
	 * 带有执行优先级的队列。是一个特殊的无界队列。
	 * ArrayBlockingQueue和LinkedBlockingQueue都是按照先进先出算法来处理任务。而PriorityBlockingQueue可根据任务自身的优先级顺序先后执行（总是确保高优先级的任务先执行）。
	 */
	PRIORITY_BLOCKING_QUEUE(3, "PriorityBlockingQueue"),

	DELAY_QUEUE(4, "DelayQueue"),

	/**
	 * 直接提交的任务队列（SynchronousQueue）
	 * 没有容量
	 * 提交的任务不会被真实的保存在队列中，而总是将新任务提交给线程执行。如果没有空闲的线程，则尝试创建新的线程。
	 * 如果线程数大于最大值maximumPoolSize，则执行拒绝策略。
	 */
	SYNCHRONOUS_QUEUE(5, "SynchronousQueue"),

	LINKED_TRANSFER_QUEUE(6, "LinkedTransferQueue"),

	LINKED_BLOCKING_DEQUE(7, "LinkedBlockingDeque");

	private final int code;

	private final String value;

	public static BlockingQueue<Runnable> buildLbq(String value, int capacity, boolean fair) {
		BlockingQueue<Runnable> blockingQueue = null;
		if (Objects.equals(value, ARRAY_BLOCKING_QUEUE.getValue())) {
			blockingQueue = new ArrayBlockingQueue<>(capacity,fair);
		} else if (Objects.equals(value, LINKED_BLOCKING_QUEUE.getValue())) {
			blockingQueue = new LinkedBlockingQueue<>(capacity);
		} else if (Objects.equals(value, PRIORITY_BLOCKING_QUEUE.getValue())) {
			blockingQueue = new PriorityBlockingQueue<>(capacity);
		} else if (Objects.equals(value, DELAY_QUEUE.getValue())) {
			blockingQueue = new DelayQueue();
		} else if (Objects.equals(value, SYNCHRONOUS_QUEUE.getValue())) {
			blockingQueue = new SynchronousQueue<>(fair);
		} else if (Objects.equals(value, LINKED_TRANSFER_QUEUE.getValue())) {
			blockingQueue = new LinkedTransferQueue<>();
		} else if (Objects.equals(value, LINKED_BLOCKING_DEQUE.getValue())) {
			blockingQueue = new LinkedBlockingDeque<>(capacity);
		}
		if (blockingQueue != null) {
			return blockingQueue;
		}
		throw new DynamicThreadException("不支持的阻塞队列类型: " + value);
	}
}
