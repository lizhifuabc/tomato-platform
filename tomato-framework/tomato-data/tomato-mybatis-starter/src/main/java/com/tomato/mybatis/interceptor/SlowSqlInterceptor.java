package com.tomato.mybatis.interceptor;

import com.tomato.mybatis.TomatoMybatisProperties;
import com.tomato.mybatis.domain.ExplainResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 基于 MyBatis 插件来统计慢 SQL
 *
 * @author lizhifu
 * @since 2023/7/31
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, org.apache.ibatis.session.RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class SlowSqlInterceptor implements Interceptor {
    private final TomatoMybatisProperties tomatoMybatisProperties;
    public SlowSqlInterceptor(TomatoMybatisProperties tomatoMybatisProperties) {
        this.tomatoMybatisProperties = tomatoMybatisProperties;
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = invocation.proceed();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        if (elapsedTime > tomatoMybatisProperties.getSlowSqlMillis()) {
            Object[] args = invocation.getArgs();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            String sqlId = mappedStatement.getId();
            String methodName = invocation.getMethod().getName();
            // 记录慢 SQL 相关信息，比如 SQL 语句、参数等
            // 在这里可以将这些信息保存到日志文件或数据库中
            // 示例：logSlowSql(invocation, elapsedTime);
            log.warn("sqlId：{},methodName：{},执行时间：{}ms",sqlId,methodName,elapsedTime);

            Object parameter = args[1];
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            Executor executor = (Executor) invocation.getTarget();
            // 执行 explain 语句，获取 SQL 执行计划,并记录到日志文件或数据库中,方便后续分析
            sqlExplain(mappedStatement.getConfiguration(),mappedStatement,boundSql,executor.getTransaction().getConnection(),parameter);
            // 动态 替换 SQL 语句，不重启修复慢 SQL TODO
        }
        return result;
    }
    private void sqlExplain(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Connection connection, Object parameter) {
        // sqlExplain 为 explain + 原始 sql
        String sqlExplain = "EXPLAIN " + boundSql.getSql();
        // 通过 StaticSqlSource 封装 explain_sql
        StaticSqlSource sqlSource = new StaticSqlSource(configuration, sqlExplain, boundSql.getParameterMappings());
        // 构建一个新的MappedStatement对象
        MappedStatement.Builder builder = new MappedStatement.Builder(configuration, "explain_sql", sqlSource, SqlCommandType.SELECT);
        MappedStatement queryStatement = builder.build();
        // 设置结果映射和语句类型等信息
        builder.resultMaps(mappedStatement.getResultMaps()).resultSetType(mappedStatement.getResultSetType()).statementType(mappedStatement.getStatementType());
        // 使用ParameterHandler设置SQL参数
        DefaultParameterHandler handler = new DefaultParameterHandler(queryStatement, parameter, boundSql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sqlExplain);
            handler.setParameters(stmt);
            ResultSet rs = stmt.executeQuery();
            List<ExplainResult> explainResultList = new ArrayList<>();
            while (rs.next()){
                // 记录所有字段值
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    ExplainResult explainResult = new ExplainResult();
                    explainResult.setId(rs.getString("id"));
                    explainResult.setSelectType(rs.getString("select_type"));
                    explainResult.setTable(rs.getString("table"));
                    explainResult.setPartitions(rs.getString("partitions"));
                    explainResult.setType(rs.getString("type"));
                    explainResult.setPossibleKeys(rs.getString("possible_keys"));
                    explainResult.setKey(rs.getString("key"));
                    explainResult.setKeyLen(rs.getString("key_len"));
                    explainResult.setRef(rs.getString("ref"));
                    explainResult.setRows(rs.getString("rows"));
                    explainResult.setFiltered(rs.getString("filtered"));
                    explainResult.setExtra(rs.getString("Extra"));
                    explainResultList.add(explainResult);
                }
            }
            log.warn("explain sql 执行结果为:{}",explainResultList);
        } catch (SQLException e) {
            log.error("explain sql 执行出现异常:{}",e.getMessage());
        }
    }
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
