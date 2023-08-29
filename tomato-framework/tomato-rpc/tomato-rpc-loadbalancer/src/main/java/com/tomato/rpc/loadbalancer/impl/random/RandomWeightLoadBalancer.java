package com.tomato.rpc.loadbalancer.impl.random;

import com.tomato.rpc.common.domain.ServiceMetadata;

import java.util.List;

/**
 * 基于加权随机算法的负载均衡策略
 *
 * @author lizhifu
 * @since 2023/5/25
 */
public class RandomWeightLoadBalancer extends RandomLoadBalancer {

	@Override
	protected ServiceMetadata doChoose(List<ServiceMetadata> list, String source, int hashCode) {
		// 根据权重重新生成服务元数据列表，权重越高的元数据，会在最终的列表中出现的次数越多
		// 比如：假设有两个服务元数据，权重分别为 1 和 2，那么重新生成的列表为 [0, 1, 1]
		// 这样就相当于将权重为 2 的服务元数据出现了两次，权重为 1 的服务元数据出现了一次
		list = weight(list);
		return super.doChoose(list, source, hashCode);
	}

}
