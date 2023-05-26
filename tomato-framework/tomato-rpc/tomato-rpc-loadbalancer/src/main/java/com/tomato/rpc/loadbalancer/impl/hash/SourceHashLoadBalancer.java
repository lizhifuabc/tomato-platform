package com.tomato.rpc.loadbalancer.impl.hash;

import com.tomato.rpc.common.domain.ServiceMetadata;
import com.tomato.rpc.loadbalancer.AbsLoadBalancer;

import java.util.List;

/**
 * 基于源IP地址Hash的负载均衡策略
 * 源地址哈希的思想是获取客户端访问的IP地址值，通过哈希函数计算得到一个数值，用该数值对服务器列表的大小进行取模运算，
 * 得到的结果便是要访问的服务器的序号。
 * 源地址哈希法的优点在于：保证了相同客户端IP地址将会被哈希到同一台后端服务器，
 * 直到后端服务器列表变更。根据此特性可以在服务消费者与服务提供者之间建立有状态的session会话。
 * 源地址哈希算法的缺点在于：除非集群中服务器的非常稳定，基本不会上下线，否则一旦有服务器上线、下线，
 * 那么通过源地址哈希算法路由到的服务器是服务器上线、下线前路由到的服务器的概率非常低，
 * 如果是session则取不到session，如果是缓存则可能引发”雪崩”。
 *
 * @author lizhifu
 * @since 2023/5/25
 */
public class SourceHashLoadBalancer extends AbsLoadBalancer {
    @Override
    protected ServiceMetadata doSelect(List<ServiceMetadata> list,String source, int hashCode) {
        int resultHashCode = Math.abs(source.hashCode() + hashCode);
        return list.get(resultHashCode % list.size());
    }
}
