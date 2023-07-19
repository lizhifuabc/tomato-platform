package com.tomato.cloud.gateway.gray.constant;

import com.tomato.common.enums.BaseEnum;

/**
 * 发布类型
 *
 * @author lizhifu
 * @since 2023/7/18
 */
public enum DeploymentType implements BaseEnum<String> {
    /**
     * 蓝绿发布：
     * 不停机旧版本，部署新版本，通过用户标记将流量在新版本和老版本切换。属无损发布
     * 优点：新版本升级和老版本回滚迅速。用户可以灵活控制流量走向
     * 缺点：成本较高，需要部署两套环境（蓝/绿）
     */
    BLUE_GREEN("BLUE_GREEN", "蓝绿发布"),
    /**
     * 灰度发布（金丝雀发布 Canary）
     * 不停机旧版本，部署新版本，高比例流量（例如：95%）走旧版本，低比例流量（例如：5%）切换到新版本，
     * 通过监控观察无问题，逐步扩大范围，最终把所有流量都迁移到新版本上。属无损发布
     * 优点：灵活简单，不需要用户标记驱动。安全性高，新版本如果出现问题，只会发生在低比例的流量上
     * 缺点：成本较高，需要部署两套环境（灰/正）
     */
    GRAY("GRAY", "灰度发布"),
    /**
     * 滚动发布（Rolling）:
     * 每次只升级一个或多个服务，通过观察无问题，不断执行这个过程，直到集群中的全部旧版本升级到新版本。属有损发布.
     * 优点：成本低，只需要部署一套环境
     * 缺点：升级速度慢，升级过程中，会出现新旧版本共存的情况，需要考虑兼容性问题,无法快速回滚，必须重新降级部署。发布和回滚需要较长的时间周期
     */
    ROLLING("ROLLING", "滚动发布"),
    ;

    DeploymentType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getValue() {
        return value;
    }
    private final String value;

    private final String desc;
}
