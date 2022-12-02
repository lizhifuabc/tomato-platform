package com.tomato.domain.resp;

/**
 * 返回数据结构
 *
 * @author lizhifu
 * @date 2022/11/21
 */
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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
