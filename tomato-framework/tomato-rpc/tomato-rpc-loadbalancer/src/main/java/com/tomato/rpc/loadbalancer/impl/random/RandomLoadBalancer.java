package com.tomato.rpc.loadbalancer.impl.random;

import com.tomato.rpc.common.domain.ServiceMetadata;
import com.tomato.rpc.loadbalancer.AbsLoadBalancer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 基于随机算法的负载均衡策略
 *
 * @author lizhifu
 * @since 2023/5/25
 */
public class RandomLoadBalancer extends AbsLoadBalancer {
    @Override
    protected ServiceMetadata doChoose(List<ServiceMetadata> list, String source, int hashCode) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
