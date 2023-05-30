package com.tomato.rpc.loadbalancer.impl.least;

import com.tomato.rpc.common.domain.ServiceMetadata;
import com.tomato.rpc.loadbalancer.AbsLoadBalancer;

import java.util.Collections;
import java.util.List;

/**
 * 基于最少连接数的负载均衡策略
 *
 * @author lizhifu
 * @since 2023/5/26
 */
public class LeastConnectionLoadBalancer extends AbsLoadBalancer {
    @Override
    protected ServiceMetadata doChoose(List<ServiceMetadata> list, String source, int hashCode) {
        // 获取所有服务器的连接数信息
        List<Integer> connections = list.stream()
                .map(ServiceMetadata::getConnectionCount)
                .toList();
        // 找到连接数最小的服务器下标
        int index = connections.indexOf(Collections.min(connections));
        // 选择该服务器,并更新其连接数
        ServiceMetadata server = list.get(index);
        // TODO 放在这里是为了方便测试，实际上应该在客户端调用成功后更新连接数
        server.setConnectionCount(server.getConnectionCount() + 1);
        return server;
    }
}
