package com.tomato.reconciliation.utils;

import com.tomato.reconciliation.dbinfo.internal.domain.DbInfo;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * 执行查询
 *
 * @author lizhifu
 * @since 2023/5/27
 */
@Slf4j
public class ExecuteQueryUtil {
    /**
     * 执行查询
     * @param db 数据源
     * @param sql sql
     * @return 结果集
     */
    public static List<Map<String, Object>> query(DbInfo db, String sql) {
        // 结果集
        List<Map<String, Object>> result = new ArrayList<>();
        // 需要关闭资源
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            // 获取结果集元数据
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                // 每行结果 Map
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String column;
                    // as 别名
                    String columnLabel = metaData.getColumnLabel(i);
                    if (columnLabel == null || "".equals(columnLabel)) {
                        // 列名
                        column = metaData.getColumnName(i);
                    } else {
                        column = columnLabel;
                    }
                    Object columnValue;
                    // TODO 其他类型
                    switch (metaData.getColumnType(i)) {
                        case Types.DECIMAL:
                            // 获取有效数字位数
                            BigDecimal bd = rs.getBigDecimal(i);
                            columnValue = bd.toPlainString();
                            break;
                        default:
                            columnValue = rs.getObject(i);
                            break;
                    }
                    row.put(column, columnValue);
                }
                result.add(row);
            }
        }catch (Exception e){
            log.error("ExecuteQueryUtil query error",e);
        }finally {
            // 释放资源
            Optional.ofNullable(rs).ifPresent((r)->{
                try {
                    r.close();
                } catch (SQLException ignored) {
                }
            });
            Optional.ofNullable(stmt).ifPresent((s)->{
                try {
                    s.close();
                } catch (SQLException ignored) {
                }
            });
            Optional.ofNullable(conn).ifPresent((c)->{
                try {
                    c.close();
                } catch (SQLException ignored) {
                }
            });
        }
        return result;
    }
}
