package com.tomato.gen.domain.bo;

import com.tomato.gen.domain.resp.TableColumnResp;
import com.tomato.gen.domain.resp.TableResp;
import lombok.Data;

import java.util.List;

/**
 * 表信息
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@Data
public class TableBo {
    /**
     * 表信息
     */
    private TableResp table;
    /**
     * 列信息
     */
    private List<TableColumnResp> tableColumnList;
}
