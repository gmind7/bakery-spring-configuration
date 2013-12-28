package com.gmind7.bakery.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import com.gmind7.bakery.config.redis.RedisNode;
import com.gmind7.bakery.config.redis.RedisSourceTemplate;

@Configuration
public class KeyValueConfig {

	@Inject
	private Environment environment;
	
	@Bean
	public RedisConnectionFactory redisDefaultConnectionFactory() {
		JedisConnectionFactory redis = new JedisConnectionFactory();
		redis.setHostName(environment.getRequiredProperty("redis.default.host"));
		redis.setPort(environment.getRequiredProperty("redis.default.port", Integer.class));
		redis.setPoolConfig(jedisPoolConfig());
		redis.setUsePool(true);
		return redis;
	}
	
	@Bean
	public RedisConnectionFactory redisGame1ConnectionFactory() {
		JedisConnectionFactory redis = new JedisConnectionFactory();
		redis.setHostName(environment.getRequiredProperty("redis.game1.host"));
		redis.setPort(environment.getRequiredProperty("redis.game1.port", Integer.class));
		redis.setPoolConfig(jedisPoolConfig());
		redis.setUsePool(true);
		return redis;
	}
	
	@Bean
	public RedisConnectionFactory redisGame2ConnectionFactory() {
		JedisConnectionFactory redis = new JedisConnectionFactory();
		redis.setHostName(environment.getRequiredProperty("redis.game2.host"));
		redis.setPort(environment.getRequiredProperty("redis.game2.port", Integer.class));
		redis.setPoolConfig(jedisPoolConfig());
		redis.setUsePool(true);
		return redis;
	}
	
	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxActive(environment.getRequiredProperty("redis.base.maxactive", Integer.class));
		jedisPoolConfig.setMaxIdle(environment.getRequiredProperty("redis.base.maxidle", Integer.class));		
		jedisPoolConfig.setMaxWait(environment.getRequiredProperty("redis.base.maxwait", Integer.class));
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(environment.getRequiredProperty("redis.base.timebetweenevictionrunsmillis", Integer.class));
		jedisPoolConfig.setTestWhileIdle(environment.getRequiredProperty("redis.base.testwhileidle", Boolean.class));
		return jedisPoolConfig;
	}
	
	public RedisTemplate<String, Object> serializerRedisTemplate() {
		
        RedisTemplate<String, Object> template =  new RedisTemplate<String, Object>();
        
        template.setKeySerializer(new StringRedisSerializer());
        //template.setValueSerializer(new StringRedisSerializer());
      
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        //template.setHashValueSerializer(new JacksonJsonRedisSerializer<App>(App.class));
        
        return template;
    }
	
	@Bean(name="defaultRedisTemplate")
    public RedisTemplate<String, Object> defaultRedisTemplate() {
		
        RedisTemplate<String, Object> template =  serializerRedisTemplate();
        template.setConnectionFactory(redisDefaultConnectionFactory());
          
        return template;
    }
	
	@Bean(name="gameRedis1Template")
    public RedisTemplate<String, Object> gameRedis1Template() {
		
        RedisTemplate<String, Object> template = serializerRedisTemplate();
        template.setConnectionFactory(redisGame1ConnectionFactory());
        
        return template;
    }
	
	@Bean(name="gameRedis2Template")
    public RedisTemplate<String, Object> gameRedis2Template() {
		
        RedisTemplate<String, Object> template = serializerRedisTemplate();
        template.setConnectionFactory(redisGame2ConnectionFactory());
        
        return template;
    }
	
	@Bean
	public RedisSourceTemplate redisTemplate() {
		RedisSourceTemplate routingRedisTemplate = new RedisSourceTemplate();
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY0xx, gameRedis1Template());
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY1xx, gameRedis2Template());
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY2xx, gameRedis1Template());
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY3xx, gameRedis1Template());
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY4xx, gameRedis1Template());
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY5xx, gameRedis2Template());
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY6xx, gameRedis2Template());
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY7xx, gameRedis2Template());
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY8xx, gameRedis2Template());
		routingRedisTemplate.putRedisTemplate(true, RedisNode.BAKERY9xx, gameRedis2Template());
		routingRedisTemplate.putRedisTemplate(false, RedisNode.DEFAULT, defaultRedisTemplate());
		return routingRedisTemplate;
	}
	
}
