package com.tomato.mybatis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 属性配置
 *
 * @author lizhifu
 * @since 2023/5/23
 */
@ConfigurationProperties(prefix = "tomato.mybatis")
@Data
public class TomatoMybatisProperties {
    /**
     * 表前缀
     */
    private static final String TABLE_PREFIX = "t_";

    /**
     * 主键名
     */
    public static final String DEFAULT_PRIMARY_KEY = "id";
}
