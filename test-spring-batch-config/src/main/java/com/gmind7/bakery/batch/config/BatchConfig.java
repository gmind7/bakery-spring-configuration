package com.gmind7.bakery.batch.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.gmind7.bakery.config.BatchConfigurerAdapter;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@ComponentScan
public class BatchConfig extends BatchConfigurerAdapter {

	public static void main(String[] args) throws Exception {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchConfig.class, args)));
	}

	@Override
	public DataSource getBatchDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlatformTransactionManager getTransactionManager() {
		// TODO Auto-generated method stub
		return null;
	}
	
}