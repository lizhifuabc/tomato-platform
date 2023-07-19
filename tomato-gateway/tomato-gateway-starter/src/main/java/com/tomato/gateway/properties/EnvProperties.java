package com.tomato.gateway.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 环境信息
 *
 * @author lizhifu
 * @since 2023/7/18
 */
@Data
@Configuration
@RefreshScope
public class EnvProperties {
    @Value("${env.pro.version}")
    private String proVersion;

    @Value("${env.gray.users}")
    private List<String> grayUsers;

    @Value("${env.gray.version}")
    private String grayVersion;
}
