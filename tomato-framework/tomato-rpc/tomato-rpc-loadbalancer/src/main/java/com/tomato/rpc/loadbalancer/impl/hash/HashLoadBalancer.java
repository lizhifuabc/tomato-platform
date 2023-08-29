package com.tomato.rpc.loadbalancer.impl.hash;

import com.tomato.rpc.common.domain.ServiceMetadata;
import com.tomato.rpc.loadbalancer.AbsLoadBalancer;

import java.util.List;

/**
 * 基于Hash算法的负载均衡策略
 *
 * @author lizhifu
 * @since 2023/5/26
 */
public class HashLoadBalancer extends AbsLoadBalancer {

	@Override
	protected ServiceMetadata doChoose(List<ServiceMetadata> list, String source, int hashCode) {
		int index = Math.abs(hashCode) % list.size();
		return list.get(index);
	}

}
