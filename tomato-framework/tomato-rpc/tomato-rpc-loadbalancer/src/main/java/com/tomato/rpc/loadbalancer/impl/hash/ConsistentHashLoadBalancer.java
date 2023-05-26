package com.tomato.rpc.loadbalancer.impl.hash;

import com.tomato.rpc.common.domain.ServiceMetadata;
import com.tomato.rpc.loadbalancer.AbsLoadBalancer;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 基于一致性Hash负载均衡策略
 *
 * @author lizhifu
 * @since 2023/5/26
 */
public class ConsistentHashLoadBalancer extends AbsLoadBalancer {
    /**
     * 虚拟节点个数
     */
    private final static int VIRTUAL_NODE = 10;
    /**
     * 虚拟节点分隔符
     */
    private final static String VIRTUAL_NODE_SPLIT = "#";
    @Override
    protected ServiceMetadata doSelect(List<ServiceMetadata> list, String source, int hashCode) {
        TreeMap<Integer, ServiceMetadata> ring = ring(list);
        return doSelect(ring, hashCode);
    }
    private ServiceMetadata doSelect(TreeMap<Integer, ServiceMetadata> ring, int hashCode) {
        Map.Entry<Integer, ServiceMetadata> entry = ring.ceilingEntry(hashCode);
        if (entry == null) {
            entry = ring.firstEntry();
        }
        return entry.getValue();
    }
    /**
     * 构建Hash环
     * @param servers 服务列表
     * @return Hash环
     */
    private TreeMap<Integer, ServiceMetadata> ring(List<ServiceMetadata> servers) {
        TreeMap<Integer, ServiceMetadata> ring = new TreeMap<>();
        for (ServiceMetadata instance : servers) {
            for (int i = 0; i < VIRTUAL_NODE; i++) {
                ring.put((key(instance) + VIRTUAL_NODE_SPLIT + i).hashCode(), instance);
            }
        }
        return ring;
    }
    private String key(ServiceMetadata instance) {
        return String.join(":", instance.getServiceAddr(), String.valueOf(instance.getServicePort()));
    }
}
