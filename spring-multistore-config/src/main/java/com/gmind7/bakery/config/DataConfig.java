package com.gmind7.bakery.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gmind7.bakery.config.db.DataSourceType;
import com.gmind7.bakery.config.db.RoutingDataSource;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true, order = 2)
public class DataConfig {

	@Inject
	private Environment environment;
	
	@Bean(name = "parentDatasource", destroyMethod = "close")
	@Scope("prototype")
	public DataSource parentDatasource() {
		DataSource ds = new DataSource();		
		ds.setDriverClassName(environment.getRequiredProperty("ds.service.jdbc.driverClassName"));
		ds.setMaxActive(environment.getRequiredProperty("ds.service.jdbc.maxActive", int.class));
		ds.setMaxWait(environment.getRequiredProperty("ds.service.jdbc.maxWait", int.class));
		ds.setMinIdle(environment.getRequiredProperty("ds.service.jdbc.minIdle", int.class));
		ds.setInitialSize(environment.getRequiredProperty("ds.service.jdbc.initialSize", int.class));
		ds.setValidationQuery(environment.getRequiredProperty("ds.service.jdbc.validationQuery"));
		ds.setValidationInterval(environment.getRequiredProperty("ds.service.jdbc.validationInterval", long.class));
		ds.setTestOnBorrow(environment.getRequiredProperty("ds.service.jdbc.testOnBorrow", boolean.class));
		ds.setTestWhileIdle(environment.getRequiredProperty("ds.service.jdbc.testWhileIdle", boolean.class));
		ds.setTimeBetweenEvictionRunsMillis(environment.getRequiredProperty("ds.service.jdbc.timeBetweenEvictionRunsMillis", int.class));
		ds.setRemoveAbandoned(environment.getRequiredProperty("ds.service.jdbc.removeAbandoned", boolean.class));
		ds.setRemoveAbandonedTimeout(environment.getRequiredProperty("ds.service.jdbc.removeAbandonedTimeout", int.class));
		ds.setLogAbandoned(environment.getRequiredProperty("ds.service.jdbc.logAbandoned", boolean.class));
		ds.setAbandonWhenPercentageFull(environment.getRequiredProperty("ds.service.jdbc.abandonWhenPercentageFull", int.class));
		ds.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer;org.apache.tomcat.jdbc.pool.interceptor.ConnectionState");
		ds.setConnectionProperties("autoReconnect=true;roundRobinLoadBalance=true;characterEncoding=UTF-8;autoReconnectForPools=true;elideSetAutoCommits=true;logger=Slf4JLogger");
		return ds;
	}

	@Bean(name = "bakeryDataSource", destroyMethod = "close")
	public DataSource bakeryDataSource() {
		DataSource ds = parentDatasource();
		ds.setUrl(environment.getRequiredProperty("ds.service.jdbc.bakery.url"));
		ds.setUsername(environment.getRequiredProperty("ds.service.jdbc.bakery.username"));
		ds.setPassword(environment.getRequiredProperty("ds.service.jdbc.bakery.password"));
		return ds;
	}
	
	@Bean(name = "dataSource")
	public RoutingDataSource dataSource() {
		RoutingDataSource routingDateSource = new RoutingDataSource();
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put(DataSourceType.DEFAULT, bakeryDataSource());
		targetDataSources.put(DataSourceType.BAKERY, bakeryDataSource());
		routingDateSource.setTargetDataSources(targetDataSources);
		routingDateSource.setDefaultTargetDataSource(bakeryDataSource());
		return routingDateSource;
	}
}
