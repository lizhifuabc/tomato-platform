package com.tomato.cloud.gateway.gray.version;

import com.tomato.cloud.gateway.filter.FilterOrder;
import com.tomato.cloud.gateway.gray.constant.GrayMetadataConstant;
import com.tomato.cloud.gateway.gray.constant.HeaderConstant;
import com.tomato.cloud.gateway.gray.properties.GrayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 根据请求头参数获取灰度版本
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@Slf4j
public class HeaderGrayVersionStrategy implements GrayVersionStrategy {
    @Override
    public String getVersion(RequestData requestData, GrayProperties.GrayServiceInfo grayServiceInfo) {
        // 1. header 获取 userId 的值
        String userId = requestData.getHeaders().getFirst(HeaderConstant.USER_ID);
        // 2. 如果 userId 为空,则返回未知版本
        if (userId == null) {
            return GrayMetadataConstant.VersionStatus.UN_KNOW.getValue();
        }
        List<GrayProperties.GrayServiceInfo.GrayVersionInfo> versions = grayServiceInfo.getVersions();
        // 3. 版本列表为空,则返回未知版本
        if (CollectionUtils.isEmpty(versions)) {
            return GrayMetadataConstant.VersionStatus.UN_KNOW.getValue();
        }
        // 4. 如果不匹配,则返回未知版本
        String version=  grayServiceInfo.getVersions().stream()
                // 判断请求头是否匹配每个版本的headerParam规则
                .filter(item -> item.getHeaderParam().entrySet().stream().anyMatch(head -> head.getValue().contains(userId)))
                .findFirst()
                .map(GrayProperties.GrayServiceInfo.GrayVersionInfo::getVersion)
                .orElse(GrayMetadataConstant.VersionStatus.UN_KNOW.getValue());
        log.info("灰度配置|HeaderGrayVersionStrategy|获取到的版本号是:{}|grayService:{}", version, grayServiceInfo);
        return version;
    }
    /**
     * 排序
     * @return int 排序
     */
    @Override
    public int getOrder() {
        return FilterOrder.HEADER_GRAY_VERSION;
    }
}