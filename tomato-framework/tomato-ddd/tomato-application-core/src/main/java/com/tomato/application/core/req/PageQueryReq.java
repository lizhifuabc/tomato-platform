package com.tomato.application.core.req;

/**
 * 分页查询请求
 *
 * @author lizhifu
 * @date 2022/11/21
 */
public class PageQueryReq {
    /**
     * 默认每页数量
     */
    private static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 每页数量
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 页码
     */
    private int pageIndex = 1;
    /**
     * 是否查询总数
     */
    private boolean needTotalCount = true;

    public int getPageIndex() {
        if (pageIndex < 1) {
            return 1;
        }
        return pageIndex;
    }

    public PageQueryReq setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public PageQueryReq setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
        return this;
    }

    public int getOffset() {
        return (getPageIndex() - 1) * getPageSize();
    }

    public boolean isNeedTotalCount() {
        return needTotalCount;
    }

    public void setNeedTotalCount(boolean needTotalCount) {
        this.needTotalCount = needTotalCount;
    }

}
