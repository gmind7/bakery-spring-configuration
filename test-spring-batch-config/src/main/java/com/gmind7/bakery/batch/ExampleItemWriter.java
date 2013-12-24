package com.gmind7.bakery.batch;

import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

@Named
public class ExampleItemWriter implements ItemWriter<Object> {
	
	protected Logger log = LoggerFactory.getLogger(ExampleItemWriter.class);
	
	public void write(List<? extends Object> data) throws Exception {
		log.debug("<<<<<<<< Spring Batch ExampleItemWriter Success");
	}

}
