package com.tomato.mybatis.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JSON 字段类型处理器
 * mapper里json型字段到类的映射。
 * 用法一:
 * 入库：#{jsonDataField, typeHandler=com.tomato.mybatis.handler.JacksonTypeHandler}
 * 出库：
 * <resultMap>
 * <result property="jsonDataField" column="json_data_field" javaType="com.xxx.MyClass" typeHandler="com.tomato.mybatis.handler.JacksonTypeHandler"/>
 * </resultMap>
 *
 * 用法二：
 * 1）在mybatis-config.xml中指定handler:
 *      <typeHandlers>
 *              <typeHandler handler="com.tomato.mybatis.handler.JacksonTypeHandler" javaType="com.xxx.MyClass"/>
 *      </typeHandlers>
 * 2)在MyClassMapper.xml里直接select/update/insert
 *
 * @author lizhifu
 * @since 2023/1/5
 */
public class JacksonTypeHandler<T extends Object> extends BaseTypeHandler<T> {
    private static final ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    private Class<T> clazz;
    public JacksonTypeHandler(Class<T> clazz) {
        if (null == clazz) {
            throw new PersistenceException("Type argument cannot be null");
        }
        this.clazz = clazz;
    }
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName), clazz);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex), clazz);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex), clazz);
    }
    private T toObject(String content, Class<?> clazz) {
        if (content != null && !content.isEmpty()) {
            try {
                return (T) objectMapper.readValue(content, clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
    private String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
