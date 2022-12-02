package com.tomato.domain.resp;

import com.tomato.domain.resp.code.CommonRespCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 多数据返回
 *
 * @author lizhifu
 * @date 2022/11/21
 */
public class MultiResp<T> extends Resp {
    /**
     * 返回数据
     */
    private Collection<T> data;

    /**
     * 获取返回数据
     * @return 返回数据
     */
    public List<T> getData() {
        if (null == data) {
            return Collections.emptyList();
        }
        if (data instanceof List) {
            return (List<T>) data;
        }
        return new ArrayList<>(data);
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public static MultiResp buildSuccess() {
        MultiResp response = new MultiResp();
        response.setSuccess(true);
        return response;
    }

    public static MultiResp buildFailure(String code, String msg) {
        MultiResp response = new MultiResp();
        response.setSuccess(false);
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }
    public static MultiResp buildFailure(String msg) {
        MultiResp response = new MultiResp();
        response.setSuccess(false);
        response.setCode(CommonRespCode.INTERNAL_SERVER_ERROR.code());
        response.setMsg(msg);
        return response;
    }
    public static <T> MultiResp<T> of(Collection<T> data) {
        MultiResp<T> response = new MultiResp<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}