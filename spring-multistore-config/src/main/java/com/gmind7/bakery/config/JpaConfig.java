package com.gmind7.bakery.config;

import java.util.Properties;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories("com.gmind7")
@ImportResource("classpath:spring/auditing-context.xml")
public class JpaConfig {
	
	@Inject
	private DataConfig dataConfig;
	
//	@Bean(name = "ehcache")
//  public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
//      EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
//      bean.setConfigLocation(new ClassPathResource("META-INF/ehcache.xml"));
//      bean.setShared(true);
//      return bean;
//  }
	
	public Properties jpaProperties(){
		
		Properties properties = new Properties();		
		properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		properties.setProperty("hibernate.auto_close_session", "true");
		
		properties.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.generate_statistics", "true");
		
		properties.setProperty("net.sf.ehcache.configurationResourceName", "/META-INF/ehcache.xml");
		properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		
		return properties;
		
	}
	
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		
		jpaVendorAdapter.setGenerateDdl(false);
		jpaVendorAdapter.setShowSql(false);
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(jpaVendorAdapter);
		factory.setPackagesToScan("com.gmind7.**");
		factory.setMappingResources("META-INF/orm.xml");
		factory.setJpaProperties(jpaProperties());
		factory.setDataSource(dataConfig.dataSource());
		
		factory.afterPropertiesSet();
		
		return factory;
	}
	
	@Primary
	@Bean(name="transactionManager")	
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}	
	
	@Bean(name="queryDslJdbcTemplate")
	public QueryDslJdbcTemplate queryDslJdbcTemplate(){
		return new QueryDslJdbcTemplate(dataConfig.dataSource());
	}
	
}
