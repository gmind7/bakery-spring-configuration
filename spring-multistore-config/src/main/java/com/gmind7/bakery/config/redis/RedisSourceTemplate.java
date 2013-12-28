package com.gmind7.bakery.config.redis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

public class RedisSourceTemplate implements InitializingBean {
	
	protected Logger log = LoggerFactory.getLogger(RedisSourceTemplate.class);
	
	private Map<Object, Object> allRedisTemplate;
	
	private Map<Object, Object> consistenHashRedisTemplate;
	
	private RangeMap<Integer, RedisNode> rangeRedisTemplate;
	
	public RedisSourceTemplate(){
		
		this.allRedisTemplate           = Maps.newLinkedHashMap();
		this.consistenHashRedisTemplate = Maps.newLinkedHashMap();
		this.rangeRedisTemplate         = TreeRangeMap.create();
		
		this.rangeRedisTemplate.put(Range.closed(0, 999), RedisNode.DEFAULT);
		
		this.rangeRedisTemplate.put(Range.open(0,     99), RedisNode.BAKERY0xx);
		this.rangeRedisTemplate.put(Range.open(100,  199), RedisNode.BAKERY1xx);
		this.rangeRedisTemplate.put(Range.open(200,  299), RedisNode.BAKERY2xx);
		this.rangeRedisTemplate.put(Range.open(300,  399), RedisNode.BAKERY3xx);
		this.rangeRedisTemplate.put(Range.open(400,  499), RedisNode.BAKERY4xx);
		this.rangeRedisTemplate.put(Range.open(500,  599), RedisNode.BAKERY5xx);
		this.rangeRedisTemplate.put(Range.open(600,  699), RedisNode.BAKERY6xx);
		this.rangeRedisTemplate.put(Range.open(700,  799), RedisNode.BAKERY7xx);
		this.rangeRedisTemplate.put(Range.open(800,  899), RedisNode.BAKERY8xx);
		
		this.rangeRedisTemplate.put(Range.closed(901,  999), RedisNode.BAKERY9xx);
		
		
	}
	
	public Object getAllRedisTemplate(RedisNode type){
		return this.allRedisTemplate.get(type);
	}
	
	public Object getConsistentHashRedisTemplate(RedisNode type){
		return this.consistenHashRedisTemplate.get(type);
	}
	
	public Object getRangeRedisTemplate(Long id){
		int range = id >= 1000L ? Integer.parseInt(id.toString().substring(0, 3)) : id.intValue();		
		RedisNode type = this.rangeRedisTemplate.get(range);
		log.debug("<<<<<<<<<<< Redis Connect Type(Range) : {}, Range : {}, id : {}", type.name(), range, id.toString());
		return this.allRedisTemplate.get(type);
	}

	public int getAllTemplatesSize(){
		return this.allRedisTemplate.size();
	}
	
	public int getConsistentHashRedisTemplatesSize(){
		return this.consistenHashRedisTemplate.size();
	}
	
	public void putRedisTemplate(Boolean isShard, RedisNode type, Object targetRedisTemplates) {	
		if(isShard) {
			this.consistenHashRedisTemplate.put(type, targetRedisTemplates);
		}
		this.allRedisTemplate.put(type, targetRedisTemplates);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.allRedisTemplate == null) {
			throw new IllegalArgumentException("Not Found RedisTemplate' is required");
		}
	}
	
}
