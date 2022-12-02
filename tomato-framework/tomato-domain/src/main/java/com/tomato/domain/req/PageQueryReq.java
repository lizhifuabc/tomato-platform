package com.tomato.domain.req;

/**
 * 分页查询请求
 *
 * @author lizhifu
 * @date 2022/11/21
 */
public class PageQueryReq {
    /**
     * 升序排列
     */
    public static final String ASC = "ASC";
    /**
     * 降序排列
     */
    public static final String DESC = "DESC";
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
     * 排序依据
     */
    private String orderBy;
    /**
     * 默认降序排列
     */
    private String orderDirection = DESC;
    /**
     * 分组
     */
    private String groupBy;
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

    public String getOrderBy() {
        return orderBy;
    }

    public PageQueryReq setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public PageQueryReq setOrderDirection(String orderDirection) {
        if (ASC.equalsIgnoreCase(orderDirection) || DESC.equalsIgnoreCase(orderDirection)) {
            this.orderDirection = orderDirection;
        }
        return this;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public boolean isNeedTotalCount() {
        return needTotalCount;
    }

    public void setNeedTotalCount(boolean needTotalCount) {
        this.needTotalCount = needTotalCount;
    }

}
