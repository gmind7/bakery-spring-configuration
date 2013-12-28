package com.gmind7.bakery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class PropertyConfig {

	@Configuration
	@Profile("loc")
	@PropertySource("classpath:properties/environment_loc.properties")
	static class envLocalProp {
	}
	
	@Configuration
	@Profile("dev")
	@PropertySource("classpath:properties/environment_dev.properties")
	static class envDevProp {
	}
	
	@Configuration
	@Profile("live")
	@PropertySource("classpath:properties/environment_live.properties")
	static class envLiveProp {
	}
	
}
