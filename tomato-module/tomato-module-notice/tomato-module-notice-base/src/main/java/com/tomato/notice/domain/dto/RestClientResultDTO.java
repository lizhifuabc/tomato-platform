package com.tomato.notice.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * http 请求结果
 *
 * @author lizhifu
 * @since 2023/4/24
 */
@Data
@Builder
public class RestClientResultDTO {
    private boolean result;
    private String body;
}
