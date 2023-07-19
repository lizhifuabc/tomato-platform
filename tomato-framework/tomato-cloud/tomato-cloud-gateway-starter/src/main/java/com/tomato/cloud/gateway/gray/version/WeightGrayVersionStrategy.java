package com.tomato.cloud.gateway.gray.version;

import com.tomato.cloud.gateway.filter.FilterOrder;
import com.tomato.cloud.gateway.gray.constant.GrayMetadataConstant;
import com.tomato.cloud.gateway.gray.constant.HeaderConstant;
import com.tomato.cloud.gateway.gray.properties.GrayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * 权重灰度
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@Slf4j
public class WeightGrayVersionStrategy implements GrayVersionStrategy {
    @Override
    public String getVersion(RequestData requestData, GrayProperties.GrayServiceInfo grayServiceInfo) {
        if (!grayServiceInfo.isWeightEnabled()) {
            return GrayMetadataConstant.VersionStatus.DEFAULT.getValue();
        }
        // 获取请求头
        HttpHeaders httpHeaders = requestData.getHeaders();
        String disturbanceHash = httpHeaders.getFirst(HeaderConstant.DISTURBANCE_HASH);
        // 扰动函数
        Integer hash = Stream.of(disturbanceHash)
                .filter(StringUtils::hasLength)
                .findFirst()
                // 对第一个元素进行哈希计算，取绝对值后再对 100 取模，得到最终的哈希值
                .map(i -> Math.abs(i.hashCode() % 100))
                // 返回一个随机生成的 0 到 99 之间的整数作为哈希值。
                .orElse(ThreadLocalRandom.current().nextInt(100));
        return grayVersion(grayServiceInfo.getVersions(), hash);
    }
    /**
     * 根据权重获取版本
     * @param versions 版本列表
     * @param disturbanceHash 扰动函数
     * @return String 版本
     */
    private String grayVersion(List<GrayProperties.GrayServiceInfo.GrayVersionInfo> versions, int disturbanceHash) {
        int sum = 0;
        // 迭代遍历 versions 列表中的灰度版本信息，计算每个版本的权重之和
        for (GrayProperties.GrayServiceInfo.GrayVersionInfo version : versions) {
            sum += version.getWeight();
            // 如果 weight 小于当前累加的权重值 sum，表示找到了匹配的灰度版本，记录日志并返回该版本号。
            if (disturbanceHash < sum) {
                String selectedVersion = version.getVersion();
                log.info("灰度配置|WeightGrayVersionStrategy|获取到的灰度版本:{}|disturbanceHash:{}", selectedVersion, disturbanceHash);
                return selectedVersion;
            }
        }

        log.warn("灰度配置|WeightGrayVersionStrategy|未获取到灰度版本:{}|weight:{}", GrayMetadataConstant.VersionStatus.UN_KNOW.getValue(), disturbanceHash);
        return GrayMetadataConstant.VersionStatus.UN_KNOW.getValue();
    }

    /**
     * 排序 优先级低于 {@link HeaderGrayVersionStrategy}
     * @return int 排序
     */
    @Override
    public int getOrder() {
        return FilterOrder.WEIGHT_GRAY_VERSION;
    }
}
