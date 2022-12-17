package com.tomato.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * Spring Security 配置
 *
 * @author lizhifu
 * @since 2022/12/16
 */
@ConfigurationProperties(prefix = "tomato.security")
public class SecurityProperties {
    /**
     * 免登录的 URL 列表
     */
    private List<String> permitAllUrls = Collections.emptyList();

    public List<String> getPermitAllUrls() {
        return permitAllUrls;
    }

    public void setPermitAllUrls(List<String> permitAllUrls) {
        this.permitAllUrls = permitAllUrls;
    }
}
