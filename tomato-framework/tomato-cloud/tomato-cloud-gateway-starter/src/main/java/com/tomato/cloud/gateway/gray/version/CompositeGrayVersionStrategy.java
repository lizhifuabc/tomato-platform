package com.tomato.cloud.gateway.gray.version;

import com.tomato.cloud.gateway.gray.constant.GrayMetadataConstant;
import com.tomato.cloud.gateway.gray.properties.GrayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;

/**
 * 复合灰度版本规则
 * 组合多个 GrayVersionStrategy 来决定最终的版本
 * @author lizhifu
 * @since 2023/7/18
 */
@Slf4j
public class CompositeGrayVersionStrategy implements GrayVersionStrategy {
    /**
     * 灰度版本策略列表
     */
    private final List<GrayVersionStrategy> grayVersionStrategies;
    /**
     * 灰度配置
     */
    public CompositeGrayVersionStrategy(List<GrayVersionStrategy> grayVersionStrategies) {
        // 根据@Order的值对规则进行排序,值越小,优先级越高,排序越前
        AnnotationAwareOrderComparator.sort(grayVersionStrategies);
        this.grayVersionStrategies = grayVersionStrategies;
    }
    /**
     * 获取版本
     * @param requestData 请求数据
     * @param grayServiceInfo 灰度服务信息
     * @return String 版本
     */
    @Override
    public String getVersion(RequestData requestData, GrayProperties.GrayServiceInfo grayServiceInfo) {
        log.info("3.1灰度配置|CompositeGrayVersionStrategy|灰度发布获取版本号|requestData:{},grayServiceInfo:{}",requestData,grayServiceInfo);
        // 对每个规则调用getVersion(),如果不返回未知版本UN_KNOW,则直接返回该版本
        // GrayVersionStrategy 已经按照@Order的值排序,值越小,优先级越高,排序越前
        for (GrayVersionStrategy strategy : grayVersionStrategies) {
            log.info("3.2灰度配置|CompositeGrayVersionStrategy|灰度发布获取版本号|策略名称:{}",strategy);
            String version = strategy.getVersion(requestData, grayServiceInfo);
            // 如果版本为未知版本,则继续执行下一个规则
            if (GrayMetadataConstant.VersionStatus.UN_KNOW.getValue().equals(version)) {
                continue;
            }
            return version;
        }
        return GrayMetadataConstant.VersionStatus.UN_KNOW.getValue();
    }
}