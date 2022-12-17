package com.tomato.security.code;

import com.tomato.domain.resp.code.RespCode;

/**
 * 用错误码
 *
 * @author lizhifu
 * @since 2022/12/16
 */
public interface UserErrorCode {
    /**
     * 您还未登录或登录失效
     */
    RespCode LOGIN_STATE_INVALID = new RespCode("30007", "您还未登录或登录失效，请重新登录！");
}
