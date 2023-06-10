package com.tomato.sys.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * openApi配置
 *
 * @author lizhifu
 * @since 2023/6/10
 */
@OpenAPIDefinition(
        // 定义安全需求
        security = {@SecurityRequirement(name = "Authorization")})
// 定义安全方案
@SecurityScheme(
        type = SecuritySchemeType.HTTP,     // 定义类型为HTTP
        name = "Authorization",             // 名称
        description = "Authorization token",// 简介
        scheme = "bearer",                  // 前缀
        bearerFormat = "JWT",               // 承载格式
        in = SecuritySchemeIn.HEADER        // 定义位置
)
public class OpenApiConfig {
}
