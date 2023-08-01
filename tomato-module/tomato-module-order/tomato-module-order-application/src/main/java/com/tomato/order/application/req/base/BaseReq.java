package com.tomato.order.application.req.base;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 基础 DTO
 *
 * @author lizhifu
 * @since 2023/4/7
 */
@Data
public class BaseReq {
    @NotBlank(message="hmac不能为空")
    private String hmac;
}
