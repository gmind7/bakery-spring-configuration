package com.gmind7.bakery.config.redis;

import java.util.HashMap;
import java.util.Map;

public enum RedisNode {
	
	BAKERY0xx(0), 
	BAKERY1xx(100),
	BAKERY2xx(200),
	BAKERY3xx(300),
	BAKERY4xx(400),
	BAKERY5xx(500),
	BAKERY6xx(600),
	BAKERY7xx(700),
	BAKERY8xx(800),
	BAKERY9xx(900),
	DEFAULT(999);
	
	private static final Map<Integer, RedisNode> $REDIS_NODE_MAP = new HashMap<Integer, RedisNode>();
	
	static {
	    for (RedisNode type : RedisNode.values()) {
	    	$REDIS_NODE_MAP.put(type.getCode(), type);
	    }
	}
	
	RedisNode(int code) {
	    this.code = code;
	}
	
	private int code;

	public int getCode() {
		return code;
	}
	
	public static RedisNode findByCode(int code){
		return $REDIS_NODE_MAP.get(code);
	}
	
}
