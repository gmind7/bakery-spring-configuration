package com.gmind7.bakery.batch;

import javax.inject.Named;

import org.springframework.batch.item.ItemReader;

@Named
public class ExampleItemReader implements ItemReader<String> {
	
	private String[] input = {"Hello world!", null};
	
	private int index = 0;
	
	public String read() throws Exception {
		if (index < input.length) {
			return input[index++];
		} else {
			return null;
		}
		
	}

}
