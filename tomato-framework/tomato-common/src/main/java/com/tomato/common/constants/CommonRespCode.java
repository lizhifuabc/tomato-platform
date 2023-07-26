package com.tomato.common.constants;

import com.tomato.common.resp.RespCode;

/**
 * 统一错误码
 *
 * @author lizhifu
 * @since  2022/11/22
 */
public interface CommonRespCode {
    int SC_OK = 200;
    int SC_INTERNAL_SERVER_ERROR = 500;
    /**
     * 系统异常
     */
    RespCode INTERNAL_SERVER_ERROR = new RespCode("500", "系统异常");
    /**
     * 数据重复
     */
    RespCode DUPLICATE_KEY_SERVER_ERROR = new RespCode("501", "数据重复");
    /**
     * 成功
     */
    RespCode SUCCESS = new RespCode("200", "请求成功");
    /**
     * 请求失败
     */
    RespCode FAIL = new RespCode("400", "请求失败");
}
