package com.tomato.mybatis.paginate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 分页
 * @author lizhifu
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int INIT_VALUE = -1;

    private List<T> records = Collections.emptyList();
    private int pageNumber = INIT_VALUE;
    /**
     * 分页数
     */
    private int pageSize = INIT_VALUE;
    private long totalPage = INIT_VALUE;
    private long totalRow = INIT_VALUE;

    public static <T> Page<T> of(int pageNumber, int pageSize) {
        return new Page<>(pageNumber, pageSize);
    }

    public static <T> Page<T> of(int pageNumber, int pageSize, long totalRow) {
        return new Page<>(pageNumber, pageSize, totalRow);
    }

    public Page() {

    }

    public Page(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Page(int pageNumber, int pageSize, long totalRow) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalRow = totalRow;
        this.totalPage = totalRow % pageSize == 0 ? (totalRow / pageSize) : (totalRow / pageSize + 1);
    }

    public Page(List<T> records, int pageNumber, int pageSize, long totalRow) {
        this.records = records;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalRow = totalRow;
        this.totalPage = totalRow % pageSize == 0 ? (totalRow / pageSize) : (totalRow / pageSize + 1);
    }


    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
        this.totalPage = totalRow % pageSize == 0 ? (totalRow / pageSize) : (totalRow / pageSize + 1);
    }

    public <R> Page<R> map(Function<? super T, ? extends R> mapper) {
        Page<R> newPage = new Page<>();
        newPage.pageNumber = pageNumber;
        newPage.pageSize = pageSize;
        newPage.totalPage = totalPage;
        newPage.totalRow = totalRow;

        if (records != null && !records.isEmpty()) {
            List<R> newRecords = new ArrayList<>(records.size());
            for (T t : records) {
                newRecords.add(mapper.apply(t));
            }
            newPage.records = newRecords;
        }
        return newPage;
    }
}
