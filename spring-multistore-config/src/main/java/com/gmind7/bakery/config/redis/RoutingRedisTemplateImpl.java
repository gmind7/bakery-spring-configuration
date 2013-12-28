package com.gmind7.bakery.config.redis;

import java.util.zip.CRC32;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.common.hash.Hashing;

@Named
public class RoutingRedisTemplateImpl<K, V> implements RoutingRedisTemplate<K, V> {
	
	protected Logger log = LoggerFactory.getLogger(RoutingRedisTemplateImpl.class);
	
	@Autowired
	private RedisSourceTemplate redisTemplate;
	
	@Override
	@SuppressWarnings("unchecked")
	public RedisTemplate<K, V> typeShard(RedisNode type){
		log.debug("<<<<<<<<<<< Redis Connect Type(TypeAssign) : {}", type.name());
		return (RedisTemplate<K, V>)redisTemplate.getAllRedisTemplate(type);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public RedisTemplate<K, V> consistentShard(int hashKey){
		int bucket = getConsistentHashKey(hashKey);
		log.debug("<<<<<<<<<<< Redis Connect Type (ConsistentHash) : {}, Bucket : {}, HashKey : {}", RedisNode.findByCode(bucket), bucket, hashKey);
		return (RedisTemplate<K, V>)redisTemplate.getConsistentHashRedisTemplate(RedisNode.findByCode(bucket));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public RedisTemplate<K, V> rangeShard(Long id){				
		return (RedisTemplate<K, V>)redisTemplate.getRangeRedisTemplate(id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public RedisTemplate<K, V> rangeShard(String id){
		return (RedisTemplate<K, V>)redisTemplate.getRangeRedisTemplate(allocateTableIndex(id));
	}
	
	@Override
	public int getConsistentHashKey(int hashKey){
		return Hashing.consistentHash(Hashing.murmur3_128().hashInt(hashKey), redisTemplate.getConsistentHashRedisTemplatesSize());
	}
	
	private static Long allocateTableIndex(String key) {
        CRC32 c = new CRC32();
        for (int i = 0; i < key.length(); i++) {
           c.update(key.charAt(i));
        } 
        return ((Long)((c.getValue() >> 16) & 0x7fff));
	}
	
}
