package com.tomato.domain.resp.code;

import com.tomato.domain.resp.code.RespCode;

/**
 * 统一错误码
 *
 * @author lizhifu
 * @date 2022/11/22
 */
public interface CommonRespCode {
    /**
     * 系统异常
     */
    RespCode INTERNAL_SERVER_ERROR = new RespCode("500", "系统异常");
}
