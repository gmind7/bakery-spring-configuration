package com.gmind7.bakery.config;

import java.util.Properties;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages="com.gmind7.**.persistence", sqlSessionFactoryRef="mybatisSessionFactory")
public class MapperConfig {
	
	@Inject
	private DataConfig dataConfig;
	
	@Bean(name="mybatisSessionFactory")
	public SqlSessionFactoryBean mybatisSessionFactory() {
		Properties mybatisProperties = new Properties();
		mybatisProperties.setProperty("mapperLocations", "classpath*:com/gmind7/**/*Mapper.xml");
		mybatisProperties.setProperty("configLoaction", "META-INF/mapper.xml");
		mybatisProperties.setProperty("typeAliasesPackage", "com.gmind7.**");
		
		SqlSessionFactoryBean mybatisSessionFactory = new SqlSessionFactoryBean();
		mybatisSessionFactory.setDataSource(dataConfig.dataSource());
		mybatisSessionFactory.setConfigurationProperties(mybatisProperties);
		return mybatisSessionFactory;
	}
	
	@Bean(name = "mybatisTX")
	public PlatformTransactionManager mybatisTransactionManager(){
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataConfig.dataSource());
		transactionManager.setNestedTransactionAllowed(true);
		return transactionManager;
	}
	
}
