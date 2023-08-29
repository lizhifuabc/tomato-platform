package com.tomato.rpc.loadbalancer;

import com.tomato.rpc.common.domain.ServiceMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * 负载均衡抽象类
 *
 * @author lizhifu
 * @since 2023/5/26
 */
public abstract class AbsLoadBalancer implements LoadBalancer {

	@Override
	public ServiceMetadata choose(List<ServiceMetadata> list, String source, int hashCode) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		return doChoose(list, source, hashCode);
	}

	/**
	 * 选择服务元数据
	 * @param list 服务列表
	 * @param source 源地址
	 * @param hashCode hash值
	 * @return 服务元数据
	 */
	protected abstract ServiceMetadata doChoose(List<ServiceMetadata> list, String source, int hashCode);

	protected List<ServiceMetadata> weight(List<ServiceMetadata> list) {
		List<ServiceMetadata> serviceMetadataList = new ArrayList<>();
		// 根据权重重新生成服务元数据列表，权重越高的元数据，会在最终的列表中出现的次数越多
		// 比如：假设有两个服务元数据，权重分别为 1 和 2，那么重新生成的列表为 [0, 1, 1]
		// 这样就相当于将权重为 2 的服务元数据出现了两次，权重为 1 的服务元数据出现了一次
		list.forEach(serviceMetadata -> {
			for (int i = 0; i < serviceMetadata.getWeight(); i++) {
				serviceMetadataList.add(serviceMetadata);
			}
		});
		return serviceMetadataList;
	}

	protected List<ServiceMetadata> connectionCount(List<ServiceMetadata> list) {
		// 对 list 根据 connectionCount 进行排序
		list.sort((o1, o2) -> {
			if (o1.getConnectionCount() > o2.getConnectionCount()) {
				return 1;
			}
			else if (o1.getConnectionCount() < o2.getConnectionCount()) {
				return -1;
			}
			return 0;
		});
		return list;
	}

}
