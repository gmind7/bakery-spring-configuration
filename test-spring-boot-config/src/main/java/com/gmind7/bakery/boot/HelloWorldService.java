package com.gmind7.bakery.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldService {

	protected Logger log = LoggerFactory.getLogger(HelloWorldService.class);
	
	public void getHelloWorld(){
		log.debug("<<<<<<<<<<<<<<<< Hello World!! >>>>>>>>>>>>>>>>");
	}
}
