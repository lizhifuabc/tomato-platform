package com.tomato.cloud.gateway.gray.configuration;

import com.tomato.cloud.gateway.gray.constant.GrayMetadataConstant;
import com.tomato.cloud.gateway.gray.properties.GrayProperties;
import com.tomato.cloud.gateway.gray.version.GrayVersionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.loadbalancer.core.DelegatingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义服务实例获取
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@Slf4j
public class GrayServiceInstanceListSupplier extends DelegatingServiceInstanceListSupplier {
    /**
     * 灰度配置
     */
    private final GrayProperties grayProperties;
    /**
     * 灰度版本策略
     */
    private final GrayVersionStrategy grayVersionStrategy;
    public GrayServiceInstanceListSupplier(ServiceInstanceListSupplier delegate, GrayProperties grayProperties, GrayVersionStrategy grayVersionStrategy) {
        super(delegate);
        this.grayProperties = grayProperties;
        this.grayVersionStrategy = grayVersionStrategy;
    }

    /**
     * 获取服务实例
     * @return Flux List<ServiceInstance> 服务实例列表
     */
    @Override
    public Flux<List<ServiceInstance>> get() {
        return delegate.get();
    }

    /**
     * 根据 request 获取服务实例
     * @param request 请求
     * @return Flux List<ServiceInstance> 服务实例列表
     */
    @Override
    public Flux<List<ServiceInstance>> get(Request request) {
        log.info("1灰度流程|根据 request 获取服务实例");
        // 服务实例列表
        Flux<List<ServiceInstance>> listFlux = delegate.get(request);
        // 灰度配置未启用,直接返回
        if (!grayProperties.isEnabled()) {
            log.info("2灰度流程|灰度配置未启用,直接返回|实例:{}", getServiceId());
            return listFlux;
        }
        log.info("2灰度流程|灰度配置启用,根据灰度策略获取版本号|实例:{}", getServiceId());
        return listFlux.mapNotNull(instances -> {
            try {
                return filteredGrayVersion(instances, getGrayVersion(request));
            } catch (Exception e) {
                log.warn("灰度流程|GrayServiceInstanceListSupplier|发生异常|实例:{}|异常:{}", getServiceId(), e.getMessage(), e);
                return instances;
            }
        });
    }

    /**
     * 根据灰度策略获取版本号
     * @param request 请求
     * @return 版本号
     */
    private String getGrayVersion(Request request) {
        // 当前请求不是 RequestDataContext 类型,直接返回，不进行灰度处理
        if (request == null || !(request.getContext() instanceof RequestDataContext)) {
            return GrayMetadataConstant.VersionStatus.UN_KNOW.getValue();
        }
        GrayProperties.GrayServiceInfo grayServiceInfo = grayProperties.getGrayServiceInfo(getServiceId());
        if (grayServiceInfo == null) {
            log.warn("3灰度流程|GrayServiceInstanceListSupplier|未获取到灰度配置|实例:{}", getServiceId());
            return GrayMetadataConstant.VersionStatus.UN_KNOW.getValue();
        }
        String version = grayVersionStrategy.getVersion(((RequestDataContext) request.getContext()).getClientRequest(), grayServiceInfo);
        log.info("3灰度流程|GrayServiceInstanceListSupplier|获取到灰度版本号|实例:{}|版本号:{}", getServiceId(), version);
        return version;
    }
    /**
     * 根据版本号过滤服务实例
     * @param instances 服务实例列表
     * @param grayVersion 版本号
     * @return 服务实例列表
     */
    private List<ServiceInstance> filteredGrayVersion(List<ServiceInstance> instances, String grayVersion) {
        // 通过灰度策略没有获取到版本号,直接返回
        if (GrayMetadataConstant.VersionStatus.UN_KNOW.getValue().equals(grayVersion)) {
            log.info("4灰度流程|GrayServiceInstanceListSupplier|filteredGrayVersion|实例:{}|版本号:{}|直接返回", getServiceId(), grayVersion);
            return instances;
        }
        // 通过灰度策略获取到版本号,过滤服务实例
        List<ServiceInstance> grayInstances = instances.stream()
                .filter(i -> {
                    String version = i.getMetadata().getOrDefault(GrayMetadataConstant.VERSION, GrayMetadataConstant.VersionStatus.DEFAULT.getValue());
                    return version.equals(grayVersion);
                })
                .collect(Collectors.toList());
        if (grayInstances.isEmpty()) {
            log.warn("灰度流程|GrayServiceInstanceListSupplier|filteredByHintVersion|实例:{}|版本号:{}|未获取到灰度服务实例", getServiceId(), grayVersion);
            return instances;
        } else {
            log.info("灰度流程|GrayServiceInstanceListSupplier|filteredByHintVersion|实例:{}|版本号:{}|获取到灰度服务实例:{}", getServiceId(), grayVersion, grayInstances);
            return grayInstances;
        }
    }
}
