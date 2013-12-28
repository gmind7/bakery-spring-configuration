package com.gmind7.bakery.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.gmind7.bakery.config.AppConfig;

@Configuration
@EnableAutoConfiguration
@Import(AppConfig.class)
public class SpringBootRunner implements CommandLineRunner {

	@Autowired
	private HelloWorldService boot;
	
	@Override
	public void run(String... arg0) throws Exception {
		boot.getHelloWorld();
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootRunner.class, args);
	}

}
