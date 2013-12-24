package com.gmind7.bakery.config;

import java.nio.charset.Charset;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplateErrorHandler;

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
	
	@Bean(name="httpClientFactory")
	public HttpComponentsClientHttpRequestFactory httpClientFactory() {
		HttpComponentsClientHttpRequestFactory httpClientFactory = new HttpComponentsClientHttpRequestFactory();
		httpClientFactory.setConnectTimeout(3000);
		httpClientFactory.setReadTimeout(5000);
		return httpClientFactory;
	}
	
	@Bean(name="restTemplate")
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(httpClientFactory());
		restTemplate.setErrorHandler(new RestTemplateErrorHandler());
		List<HttpMessageConverter<?>> converters =new ArrayList<HttpMessageConverter<?>>();
		converters.add(new FormHttpMessageConverter());
		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		converters.add(new MappingJackson2HttpMessageConverter());
		converters.add(new Jaxb2RootElementHttpMessageConverter());
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}
	
}