package com.cheng.dynamic.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.cheng.dynamic.service.DatabaseService;

@Configuration
@MapperScan(basePackages = "com.cheng.dynamic.repository.secondary", sqlSessionTemplateRef  = "sqlSessionTemplateSecondary")
public class RepositorySecondaryConfig {
	@Autowired
    private Environment env;
    @Autowired 
    @Qualifier("primaryDS")
    private DataSource primaryDS;
    @Autowired
    private DatabaseService databaseService;

    @Bean(name="dynamicDataSource")
    public DataSource dataSource(){
    	DynamicDataSource dynamicDataSource = new DynamicDataSource();
    	dynamicDataSource.setTargetDataSources(databaseService.getMap());
    	dynamicDataSource.setDefaultTargetDataSource(primaryDS);
    	return dynamicDataSource;
    }
    
    @Bean(name="sqlSessionFactorySecondary")
    public SqlSessionFactory sqlSessionFactorySecondary() throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dataSource());
        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage2"));//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations2")));//指定xml文件位置
        return fb.getObject();
    }
    
    @Bean(name = "sqlSessionTemplateSecondary")
    public SqlSessionTemplate sqlSessionTemplateSecondary(@Qualifier("sqlSessionFactorySecondary") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    @Bean(name = "transactionManagerSecondary")
    public DataSourceTransactionManager transactionManagerSecondary() {
        return new DataSourceTransactionManager(dataSource());
    }

}