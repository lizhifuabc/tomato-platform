package com.tomato.rpc.loadbalancer;

import com.tomato.rpc.common.domain.ServiceMetadata;

import java.util.List;

/**
 * 负载均衡
 *
 * @author lizhifu
 * @since 2023/5/25
 */
public interface LoadBalancer {
    /**
     * 选择一个服务节点
     * @param list 服务列表
     * @param source 源地址
     * @param hashCode hash值
     * @return 可用的服务节点
     */
    ServiceMetadata choose(List<ServiceMetadata> list, String source, int hashCode);
}
