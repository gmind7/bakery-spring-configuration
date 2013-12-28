//package com.gmind7.bakery.config;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.inject.Inject;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.google.code.ssm.Cache;
//import com.google.code.ssm.CacheFactory;
//import com.google.code.ssm.spring.SSMCache;
//import com.gmind7.bakery.handler.CacheConfigurationFactory;
//import com.gmind7.bakery.config.CacheConfig;
//
//@Configuration
//public class GameCacheConfig implements CacheConfigurationFactory {
//
//	@Inject
//	private CacheConfig cacheConfig;
//	
//	@Override
//	public Set<SSMCache> ssmCaches() throws Exception {
//		
//		Set<SSMCache> cacheMap = new HashSet<SSMCache>();
//		// 3600(60minutes)*24(hour)*7(day) , @CacheEvict(..., "allEntries" = true) doesn't work
//		cacheMap.add(new SSMCache(gameAppCache(), 3600*24*7, false));
//		cacheMap.add(new SSMCache(gameIssueCache(), 3600*24*7, false));
//		
//		return cacheMap;
//	}
//	
//	@Bean(name="gameAppCache")
//	public Cache gameAppCache() throws Exception{
//		
//		CacheFactory cacheFactory = new CacheFactory();
//		cacheFactory.setCacheName(CacheValue.APP);
//		cacheFactory.setCacheClientFactory(cacheConfig.cacheClientFactory());
//		cacheFactory.setAddressProvider(cacheConfig.addressProvider());
//		cacheFactory.setConfiguration(cacheConfig.configuration());	
//		
//		return cacheFactory.getObject();
//		
//	}
//	
//}
