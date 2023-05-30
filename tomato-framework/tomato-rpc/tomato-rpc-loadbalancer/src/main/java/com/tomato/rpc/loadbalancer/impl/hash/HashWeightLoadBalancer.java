package com.tomato.rpc.loadbalancer.impl.hash;

import com.tomato.rpc.common.domain.ServiceMetadata;

import java.util.List;

/**
 * 基于加权Hash算法负载均衡策略
 *
 * @author lizhifu
 * @since 2023/5/26
 */
public class HashWeightLoadBalancer extends HashLoadBalancer{
    @Override
    protected ServiceMetadata doChoose(List<ServiceMetadata> list, String source, int hashCode) {
        list = weight(list);
        return super.doChoose(list,source,hashCode);
    }
}