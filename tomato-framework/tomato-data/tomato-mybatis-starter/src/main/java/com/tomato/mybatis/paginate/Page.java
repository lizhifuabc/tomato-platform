package com.tomato.mybatis.paginate;

import java.io.Serializable;

/**
 * 分页
 * @author lizhifu
 */
public class Page implements Serializable {

    public static final int NO_ROW_OFFSET = 0;
    public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;
    private final int offset;
    private final int limit;

    public Page() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public Page(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
