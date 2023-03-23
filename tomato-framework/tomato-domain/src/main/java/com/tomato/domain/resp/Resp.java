package com.tomato.domain.resp;

import com.tomato.domain.resp.code.CommonRespCode;
import lombok.Data;

/**
 * 返回数据结构
 * @Accessors(chain = true)
 * 生成的getter/setter方法返回的是this，可以链式调用
 *
 * TODO 请求ID private String requestId;，
 * TODO 例如 org.apache.skywalking.apm.toolkit.trace.TraceContext.traceId()
 * @author lizhifu
 * @date 2022/11/21
 */
@Data
//@Accessors(chain = true)
public class Resp {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 返回码
     */
    private String code;

    /**
     * 返回信息
     */
    private String msg;
    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * 成功
     * @return
     */
    public static Resp buildSuccess() {
        Resp resp = new Resp();
        resp.setSuccess(true);
        resp.setCode(CommonRespCode.SUCCESS.code());
        resp.setMsg(CommonRespCode.SUCCESS.msg());
        return resp;
    }

    /**
     * 失败
     * @param code
     * @param msg
     * @return
     */
    public static Resp buildFailure(String code, String msg) {
        Resp resp = new Resp();
        resp.setSuccess(false);
        resp.setCode(code);
        resp.setMsg(msg);
        return resp;
    }
}
