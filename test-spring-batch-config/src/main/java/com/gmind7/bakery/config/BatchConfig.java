package com.gmind7.bakery.config;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.gmind7")
@EnableTransactionManagement(proxyTargetClass = true, order = 2)
public class BatchConfig extends BatchConfigurerAdapter {
	
	@Autowired
    private ResourceLoader resourceLoader;
	
	@PostConstruct
    public void initialize() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(resourceLoader.getResource("classpath:/org/springframework/batch/core/schema-hsqldb.sql"));
        populator.setContinueOnError(true);
        DatabasePopulatorUtils.execute(populator , dataSource());
    }
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource dataSource = new DataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:mem:testdb;sql.enforce_strict_size=true");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
		return dataSource;
	}
	
	@Override
	public DataSource getBatchDataSource() {
		return dataSource();
	}

	@Override
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager();
	}
	
	public Properties jpaProperties(){
		
		Properties properties = new Properties();		
		properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		properties.setProperty("hibernate.auto_close_session", "true");
		properties.setProperty("hibernate.cache.use_query_cache", "false");
		properties.setProperty("hibernate.generate_statistics", "true");
		return properties;
		
	}
	
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setDatabase(Database.HSQL);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPackagesToScan("com.gmind7.**");
		factory.setJpaProperties(jpaProperties());
		factory.setDataSource(dataSource());
		
		factory.afterPropertiesSet();
		
		return factory;
	}
	
	@Bean(name="transactionManager")	
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}	
	
	@Bean(name="queryDslJdbcTemplate")
	public QueryDslJdbcTemplate queryDslJdbcTemplate(){
		return new QueryDslJdbcTemplate(dataSource());
	}
	
}