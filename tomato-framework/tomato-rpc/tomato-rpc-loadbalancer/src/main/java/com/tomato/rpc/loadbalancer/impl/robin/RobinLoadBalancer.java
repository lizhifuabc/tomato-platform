package com.tomato.rpc.loadbalancer.impl.robin;

import com.tomato.rpc.common.domain.ServiceMetadata;
import com.tomato.rpc.loadbalancer.AbsLoadBalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于轮询算法的负载均衡策略
 *
 * @author lizhifu
 * @since 2023/5/26
 */
public class RobinLoadBalancer extends AbsLoadBalancer {

	private final AtomicInteger index = new AtomicInteger(0);

	@Override
	protected ServiceMetadata doChoose(List<ServiceMetadata> list, String source, int hashCode) {
		// 获取当前指针位置
		int currentIndex = index.getAndIncrement();
		// 如果达到列表尾,将指针移至头部
		if (currentIndex >= list.size()) {
			index.set(0);
		}
		// 返回指针指向的服务器
		return list.get(currentIndex);
	}

}
