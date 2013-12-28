package com.gmind7.bakery.config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = "com.gmind7", excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class AppConfig {
	
	public AppConfig(){
		StringBuffer sb = new StringBuffer("\n");
		sb.append("  ____           _           _ _____   ____        _                          \n");
		sb.append(" / ___|_ __ ___ (_)_ __   __| |___  | | __ )  __ _| | _____ _ __ _   _        \n");
		sb.append("| |  _| '_ ` _ \\| | '_ \\ / _` |  / /  |  _ \\ / _` | |/ / _ \\ '__| | | |   \n");
		sb.append("| |_| | | | | | | | | | | (_| | / /   | |_) | (_| |   <  __/ |  | |_| |       \n");
		sb.append(" \\____|_| |_| |_|_|_| |_|\\__,_|/_/    |____/ \\__,_|_|\\_\\___|_|   \\__, | \n");
		sb.append("                                                                 |___/        \n");
		sb.append(":: Welcome to the Gmind7 Java Recipes__________________________________       \n");
		sb.append("\n");
		System.out.print(sb.toString());
	}
	
	@Inject
	private Environment environment;
	
	@Bean(name="messageSource")
	public MessageSource messageSource() {
		List<String> fileList = new ArrayList<String>();
        fileList.add("classpath:message/message");
        fileList.add("classpath:message/validation");
        
        String[] files = (String[])fileList.toArray(new String[fileList.size()]);
        
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(files);        
        messageSource.setDefaultEncoding("UTF-8");
        //messageSource.setAlwaysUseMessageFormat(true);
        if (environment.acceptsProfiles("dev")) {
			messageSource.setCacheSeconds(0);
		}
        return messageSource;
	}
	
	@Bean(name="messageSourceAccessor")
	public MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(messageSource());
	}
		
}