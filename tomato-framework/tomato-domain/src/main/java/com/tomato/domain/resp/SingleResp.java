package com.tomato.domain.resp;

import com.tomato.domain.resp.code.CommonRespCode;

/**
 * 单条数据返回
 *
 * @author lizhifu
 * @date 2022/11/21
 */
public class SingleResp<T> extends Resp {
    /**
     * 返回数据
     */
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static SingleResp buildSuccess() {
        SingleResp response = new SingleResp();
        response.setSuccess(true);
        return response;
    }

    public static SingleResp buildFailure(String code, String msg) {
        SingleResp response = new SingleResp();
        response.setSuccess(false);
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static SingleResp buildFailure(String msg) {
        SingleResp response = new SingleResp();
        response.setSuccess(false);
        response.setCode(CommonRespCode.INTERNAL_SERVER_ERROR.code());
        response.setMsg(msg);
        return response;
    }

    public static <T> SingleResp<T> of(T data) {
        SingleResp<T> response = new SingleResp<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    @Override
    public String toString() {
        return "SingleResp{" +
                "data=" + data +
                '}'+super.toString();
    }

}