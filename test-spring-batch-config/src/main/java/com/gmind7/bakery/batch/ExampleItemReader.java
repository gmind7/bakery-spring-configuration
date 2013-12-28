package com.gmind7.bakery.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class ExampleItemReader implements ItemReader<String> {
	
	protected Logger log = LoggerFactory.getLogger(ExampleItemReader.class);
	
	private String[] input = {"Gmind1", "Gmaind2"};
	
	private int index = 0;
	
	public String read() throws Exception {
		if (index < input.length) {
			log.debug("<<<<<<<<<<<<<<<<< ExampleItemReader : {}", input[index++]);
			return input[index++];
		} else {
			return null;
		}
		
	}

}
