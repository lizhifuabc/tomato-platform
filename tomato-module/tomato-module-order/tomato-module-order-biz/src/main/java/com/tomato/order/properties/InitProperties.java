package com.tomato.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Properties
 *
 * @author lizhifu
 * @since 2023/1/3
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "tomato.init")
public class InitProperties {
    /**
     * db 数量
     */
    private int dbCount;
    /**
     * table 数量
     */
    private int tableCount;
    /**
     * snow 配置文件位置
     */
    private String snow;
}
