package com.gmind7.bakery.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableCaching
public class CacheConfig {

	//@Inject
	//private KeyValueConfig keyValueConfig;
	
//	@Bean(name="cacheManager") 
//	public RedisCacheManager cacheManager(){
//		return new RedisCacheManager(keyValueConfig.redisTemplate());
//	}
	
	/**
	 * ehcache를 사용할 경우.......................
	 
	@Inject 
	private JpaConfig jpaConfig;
	
	@Bean(name = "cacheManager")
    public EhCacheCacheManager cacheManager() {
    	EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
    	ehCacheCacheManager.setCacheManager(jpaConfig.ehCacheManagerFactoryBean().getObject());
        return ehCacheCacheManager;
    }
    */
	
	/**
	 * memcached를 사용할 경우........................

	@Inject
	private Environment environment;
	
	@Autowired
	private CacheConfigurationFactory[] cacheConfigurationFactory;
	
	@Bean(name="cacheManager")
	public SSMCacheManager cacheManager() throws Exception{
		
		SSMCacheManager ssmCacheManager = new SSMCacheManager();
		
		Set<SSMCache> cachePool = new HashSet<SSMCache>();
		
		for (CacheConfigurationFactory cacheFactory : this.cacheConfigurationFactory) {
			Set<SSMCache> cacheMap = cacheFactory.ssmCaches();
			Iterator<SSMCache> iterator = cacheMap.iterator();
			while(iterator.hasNext()){
				cachePool.add((SSMCache)iterator.next());
			}
        }
		ssmCacheManager.setCaches(cachePool);
		
		return ssmCacheManager;
	}
	
	@Bean
	public CacheClientFactory cacheClientFactory(){
		return new MemcacheClientFactoryImpl();
	}
	
	@Bean
	public AddressProvider addressProvider(){
		DefaultAddressProvider defaultAddressProvider = new DefaultAddressProvider();
		defaultAddressProvider.setAddress(environment.getRequiredProperty("memcached.addresses"));
		return defaultAddressProvider; 
	}
	
	@Bean
	public CacheConfiguration configuration(){
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		cacheConfiguration.setConsistentHashing(true);
		return cacheConfiguration;
	}
	 */
	
	/**
	 * 
	 * infinispan을 사용할 경우	 
	@Bean
	public SpringRemoteCacheManagerFactoryBean cacheManager() {
		
		SpringRemoteCacheManagerFactoryBean remoteCacheManager = new SpringRemoteCacheManagerFactoryBean();
		remoteCacheManager.setConfigurationProperties(cacheProperties());
		
		return remoteCacheManager;
	}
	
	public Properties cacheProperties(){
		
		Properties properties = new Properties();
		
		properties.setProperty("infinispan.client.hotrod.transport_factory", environment.getRequiredProperty("cache.infinispan.client.hotrod.transport_factory"));
		properties.setProperty("infinispan.client.hotrod.server_list", environment.getRequiredProperty("cache.infinispan.client.hotrod.server_list"));
		properties.setProperty("infinispan.client.hotrod.marshaller", environment.getRequiredProperty("cache.infinispan.client.hotrod.marshaller"));
		properties.setProperty("infinispan.client.hotrod.async_executor_factory", environment.getRequiredProperty("cache.infinispan.client.hotrod.async_executor_factory"));
		properties.setProperty("infinispan.client.hotrod.default_executor_factory.pool_size", environment.getRequiredProperty("cache.infinispan.client.hotrod.default_executor_factory.pool_size"));
		properties.setProperty("infinispan.client.hotrod.default_executor_factory.queue_size", environment.getRequiredProperty("cache.infinispan.client.hotrod.default_executor_factory.queue_size"));
		properties.setProperty("infinispan.client.hotrod.hash_function_impl.1", environment.getRequiredProperty("cache.infinispan.client.hotrod.hash_function_impl.1"));
		properties.setProperty("infinispan.client.hotrod.tcp_no_delay", environment.getRequiredProperty("cache.infinispan.client.hotrod.tcp_no_delay"));
		properties.setProperty("infinispan.client.hotrod.ping_on_startup", environment.getRequiredProperty("cache.infinispan.client.hotrod.ping_on_startup"));
		properties.setProperty("infinispan.client.hotrod.request_balancing_strategy", environment.getRequiredProperty("cache.infinispan.client.hotrod.request_balancing_strategy"));
		properties.setProperty("infinispan.client.hotrod.key_size_estimate", environment.getRequiredProperty("cache.infinispan.client.hotrod.key_size_estimate"));
		properties.setProperty("infinispan.client.hotrod.value_size_estimate", environment.getRequiredProperty("cache.infinispan.client.hotrod.value_size_estimate"));
		properties.setProperty("infinispan.client.hotrod.force_return_values", environment.getRequiredProperty("cache.infinispan.client.hotrod.force_return_values"));
		
		properties.setProperty("maxActive", environment.getRequiredProperty("cache.infinispan.client.hotrod.maxActive"));
		properties.setProperty("maxTotal", environment.getRequiredProperty("cache.infinispan.client.hotrod.maxTotal"));
		properties.setProperty("maxIdle", environment.getRequiredProperty("cache.infinispan.client.hotrod.maxIdle"));
		properties.setProperty("whenExhaustedAction", environment.getRequiredProperty("cache.infinispan.client.hotrod.whenExhaustedAction"));
		properties.setProperty("timeBetweenEvictionRunsMillis", environment.getRequiredProperty("cache.infinispan.client.hotrod.timeBetweenEvictionRunsMillis"));
		properties.setProperty("minEvictableIdleTimeMillis", environment.getRequiredProperty("cache.infinispan.client.hotrod.minEvictableIdleTimeMillis"));
		properties.setProperty("testWhileIdle", environment.getRequiredProperty("cache.infinispan.client.hotrod.testWhileIdle"));
		properties.setProperty("minIdle", environment.getRequiredProperty("cache.infinispan.client.hotrod.minIdle"));
		
		return properties;
	}
	*/
}