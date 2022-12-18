package com.tomato.sys.domain.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 用户登录
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@Data
@Builder
public class LoginResp {
    /**
     * token
     */
    private String token;
}
