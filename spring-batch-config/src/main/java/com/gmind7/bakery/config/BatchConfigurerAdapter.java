package com.gmind7.bakery.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.database.support.DataFieldMaxValueIncrementerFactory;
import org.springframework.batch.item.database.support.DefaultDataFieldMaxValueIncrementerFactory;
import org.springframework.batch.support.DatabaseType;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.PlatformTransactionManager;

@EnableBatchProcessing
public abstract class BatchConfigurerAdapter implements BatchConfigurer {
	
	@Override
	public JobRepository getJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(getBatchDataSource());
		factory.setTransactionManager(getTransactionManager());
		factory.setLobHandler(lobHandler());
		factory.setDatabaseType(DatabaseType.fromMetaData(getBatchDataSource()).name());
		factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
		factory.afterPropertiesSet();
		return  (JobRepository) factory.getObject();
	}
	
	@Bean
	public JobParametersIncrementer incrementer(){
		return new RunIdIncrementer();
	}
	
	@Bean
	public DataFieldMaxValueIncrementerFactory dataFieldMaxValueIncrementerFactory(){
		return new DefaultDataFieldMaxValueIncrementerFactory(getBatchDataSource());
	}

	@Override
	public JobLauncher getJobLauncher() throws Exception {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(getJobRepository());
		launcher.setTaskExecutor(new SyncTaskExecutor());
		return launcher;
	}
	
	@Bean
	public JobOperator jobOperator() throws Exception{
		SimpleJobOperator operator = new SimpleJobOperator();
		operator.setJobExplorer((JobExplorer)jobExplorer().getObject());
		operator.setJobLauncher(getJobLauncher());
		operator.setJobRegistry(jobRegistry());
		operator.setJobRepository(getJobRepository());
		return operator;
	}
	
	@Bean
	public JobLauncher jobLauncher() throws Exception{
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(getJobRepository());
		launcher.setTaskExecutor(new SyncTaskExecutor());
		return launcher;
	}
	
	@Bean
	public JobExplorerFactoryBean jobExplorer() throws Exception {
		JobExplorerFactoryBean jobExplorer = new JobExplorerFactoryBean();
		jobExplorer.setDataSource(getBatchDataSource());
		jobExplorer.setLobHandler(lobHandler());
		return jobExplorer;
	}
	
	@Bean
	public JobRegistry jobRegistry(){
		return new MapJobRegistry();
	}
	
	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(){
		JobRegistryBeanPostProcessor beanPostProcessor = new JobRegistryBeanPostProcessor();
		beanPostProcessor.setJobRegistry(jobRegistry());
		return beanPostProcessor;
	}
	
	@Bean
	public LobHandler lobHandler(){
		return new DefaultLobHandler();
	}
	
	@Bean(destroyMethod = "close")
	public abstract DataSource getBatchDataSource();
	
	public abstract PlatformTransactionManager getTransactionManager();
	
}
