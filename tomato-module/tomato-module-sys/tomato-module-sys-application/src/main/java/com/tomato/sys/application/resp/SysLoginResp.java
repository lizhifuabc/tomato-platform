package com.tomato.sys.application.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录返回
 *
 * @author lizhifu
 * @since 2023/6/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLoginResp {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
