package com.gmind7.bakery.config.redis;

import org.springframework.data.redis.core.RedisTemplate;

public interface RoutingRedisTemplate<K,V> {
	
	public RedisTemplate<K,V> typeShard(RedisNode type);
	
	public RedisTemplate<K,V> consistentShard(int hashKey);
	
	public RedisTemplate<K,V> rangeShard(Long shardKey);
	
	public RedisTemplate<K,V> rangeShard(String shardKey);
	
	public int getConsistentHashKey(int hashKey);
	
}
