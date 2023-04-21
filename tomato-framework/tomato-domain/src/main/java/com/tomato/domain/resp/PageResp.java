package com.tomato.domain.resp;

import com.tomato.common.constants.CommonRespCode;

/**
 * 页面返回数据
 *
 * @author lizhifu
 * @since  2022/11/21
 */
public class PageResp<T> extends Resp<T> {
    private int totalCount = 0;

    private int pageSize = 1;

    private int pageIndex = 1;


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return Math.max(pageSize, 1);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
    }

    public int getPageIndex() {
        return Math.max(pageIndex, 1);
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, 1);
    }


    public int getTotalPages() {
        return this.totalCount % this.pageSize == 0 ? this.totalCount
                / this.pageSize : (this.totalCount / this.pageSize) + 1;
    }

    public static <T> PageResp<T> of(T data, int totalCount, int pageSize, int pageIndex) {
        PageResp<T> response = new PageResp<>();
        response.setSuccess(true);
        response.setCode(CommonRespCode.SUCCESS.code());
        response.setMsg(CommonRespCode.SUCCESS.msg());
        response.setData(data);
        response.setTotalCount(totalCount);
        response.setPageSize(pageSize);
        response.setPageIndex(pageIndex);
        return response;
    }
}
