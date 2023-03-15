package com.tomato.seckill.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
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
@MapperScan(basePackages = "com.tomato.kill.mapper.master", sqlSessionTemplateRef  = "masterSqlSessionTemplate")
public class MasterDataSourceConfig {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(masterDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("masterDataSource") DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }

    @Bean
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory masterSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(masterSqlSessionFactory);
    }
}
