package com.gmind7.bakery.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ExampleItemWriter<T> implements ItemWriter<T> {
	
	protected Logger log = LoggerFactory.getLogger(ExampleItemWriter.class);
	
	public void write(List<? extends T> data) throws Exception {
		for(T input : data){
			log.debug("<<<<<<<<<<<<<<<<< ExampleItemWriter : {}", (String)input);
		}
	}

}
