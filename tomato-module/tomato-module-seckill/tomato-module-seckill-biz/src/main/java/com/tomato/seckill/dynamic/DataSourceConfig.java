package com.tomato.seckill.dynamic;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 *
 * @author lizhifu
 * @since 2023/3/11
 */
@Configuration
public class DataSourceConfig {
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource master() {
        return new HikariDataSource();
    }
    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slave() {
        return new HikariDataSource();
    }
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("master") DataSource master,
                                        @Qualifier("slave") DataSource slave) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", master);
        targetDataSources.put("slave", slave);
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(master);
        return dynamicDataSource;
    }
}