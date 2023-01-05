package com.tomato.mybatis.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.io.StringReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 将JSON Clob选择为MAP<String , String>作为结果集
 *
 * @author lizhifu
 * @since 2023/1/5
 */
public class ClobJsonTypeHandler extends BaseTypeHandler<Map<String, Object>> {
    private static final ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException {
        final String parameterString = mapToJson(parameter);
        StringReader reader = new StringReader(parameterString);
        ps.setCharacterStream(i, reader, parameterString.length());
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String value = "";
        Clob clob = resultSet.getClob(columnName);
        if (clob != null) {
            int size = (int) clob.length();
            value = clob.getSubString(1L, size);
        }
        return jsonToMap(value);
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String value = "";
        Clob clob = resultSet.getClob(columnIndex);
        if (clob != null) {
            int size = (int) clob.length();
            value = clob.getSubString(1L, size);
        }
        return jsonToMap(value);
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = "";
        Clob clob = cs.getClob(columnIndex);
        if (clob != null) {
            int size = (int) clob.length();
            value = clob.getSubString(1L, size);
        }
        return jsonToMap(value);
    }
    private Map<String, Object> jsonToMap(String from) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(from, HashMap.class);
        } catch (IOException e) {
            throw new Error();
        }
    }
    private String mapToJson(Map<String, Object> from) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(from);
        } catch (IOException e) {
            throw new Error();
        }
    }
}
