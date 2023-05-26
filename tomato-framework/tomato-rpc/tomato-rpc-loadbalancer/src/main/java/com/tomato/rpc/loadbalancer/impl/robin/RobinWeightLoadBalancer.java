package com.tomato.rpc.loadbalancer.impl.robin;

import com.tomato.rpc.common.domain.ServiceMetadata;

import java.util.List;

/**
 * 基于加权轮询算法的负载均衡策略
 *
 * @author lizhifu
 * @since 2023/5/26
 */
public class RobinWeightLoadBalancer extends RobinLoadBalancer {
    @Override
    protected ServiceMetadata doSelect(List<ServiceMetadata> list,String source, int hashCode) {
        list = weight(list);
        return super.doSelect(list,source,hashCode);
    }
}
