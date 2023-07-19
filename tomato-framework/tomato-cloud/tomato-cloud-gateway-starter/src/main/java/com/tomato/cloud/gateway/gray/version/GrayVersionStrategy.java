package com.tomato.cloud.gateway.gray.version;

import com.tomato.cloud.gateway.gray.properties.GrayProperties;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.core.Ordered;

/**
 * 灰度版本规则策略
 *
 * @author lizhifu
 * @since 2023/7/18
 */
public interface GrayVersionStrategy extends Ordered {
    /**
     * 获取版本
     * @param requestData 请求数据
     * @param grayServiceInfo 灰度服务
     * @return 版本
     */
    String getVersion(RequestData requestData, GrayProperties.GrayServiceInfo grayServiceInfo);

    /**
     * 顺序:返回值越小,优先级越高,越先被加载
     * 默认返回0,表示这个组件没有特定的顺序要求,可以被Spring以任意顺序加载。
     * @return int 顺序
     */
    @Override
    default int getOrder() {
        return 0;
    }
}
