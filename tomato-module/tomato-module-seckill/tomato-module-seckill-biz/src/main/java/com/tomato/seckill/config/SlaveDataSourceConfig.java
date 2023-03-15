package com.tomato.seckill.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * q：MapperScan
 * a：扫描mapper接口，将接口实例化到spring容器中
 *
 * @author lizhifu
 * @since 2023/3/11
 */
@Deprecated
//@Configuration
@MapperScan(basePackages = "com.tomato.kill.mapper.slave", sqlSessionTemplateRef  = "slaveSqlSessionTemplate")
public class SlaveDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource slaveDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(slaveDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager slaveTransactionManager(@Qualifier("slaveDataSource") DataSource slaveDataSource) {
        return new DataSourceTransactionManager(slaveDataSource);
    }

    @Bean
    public SqlSessionTemplate slaveSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory slaveSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(slaveSqlSessionFactory);
    }
}
