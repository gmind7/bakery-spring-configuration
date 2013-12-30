package com.gmind7.bakery.config;

import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.gmind7.bakery.config.AppConfig;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		Class<?>[] rootConfigClasses = this.getRootConfigClasses();
		if (!ObjectUtils.isEmpty(rootConfigClasses)) {
			AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
			String springProfilesActvie = rootAppContext.getEnvironment().getProperty("spring.profiles.active");
			if(springProfilesActvie==null) rootAppContext.getEnvironment().setActiveProfiles("loc");
			rootAppContext.register(rootConfigClasses);
			return rootAppContext;
		}
		else {
			return null;
		}
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected javax.servlet.Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new javax.servlet.Filter[] { encodingFilter, 
			                                new HiddenHttpMethodFilter(), 
			                                new ShallowEtagHeaderFilter() };
	}
}
