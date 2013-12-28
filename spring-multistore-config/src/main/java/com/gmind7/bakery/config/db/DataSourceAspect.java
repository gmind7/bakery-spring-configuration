package com.gmind7.bakery.config.db;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gmind7.bakery.config.db.DataSource;
import com.gmind7.bakery.config.db.DataSourceContextHolder;

@Aspect
@Component
@Order(value=1)
public class DataSourceAspect {
	
	protected Logger log = LoggerFactory.getLogger(DataSourceAspect.class);
	
	@Pointcut("@within(com.gmind7.bakery.config.db.DataSource)")
	public void classPointCut() {
	}
	
	@Pointcut("@annotation(com.gmind7.bakery.config.db.DataSource)")
	public void methodPointCut() {
	}

	@Before("classPointCut()")
	public void berforeClassPointCut(JoinPoint joinPoint) {
		// class
		Class<?> targetClass = AopUtils.getTargetClass(joinPoint.getThis());
		DataSource dataSource = targetClass.getAnnotation(DataSource.class);
		if (dataSource != null)	{
			log.debug("<<<<<< DataSourceAspect BerforeClassPointCut : {}", dataSource.value());
			DataSourceContextHolder.setDataSourceType(dataSource.value());
		}
	}
	
	@Before("methodPointCut()")
	public void beforeMethodPointCut(JoinPoint joinPoint) {		
		Signature signature = joinPoint.getSignature();
		if (signature instanceof MethodSignature) {
			Method method = ((MethodSignature) signature).getMethod();
			DataSource methodDataSource = (DataSource) method.getAnnotation(DataSource.class);
			if (methodDataSource != null) {
				log.debug("<<<<<< DataSourceAspect BeforeMethodPointCut : {}", methodDataSource.value());
				DataSourceContextHolder.setDataSourceType(methodDataSource.value());
			}
		}
	}
	
	@After("classPointCut()")
	public void afterClassPointCut(JoinPoint joinPoint) throws NoSuchMethodException {
		clearPointCut();
	}

	@After("methodPointCut()")
	public void afterMethodPointCut(JoinPoint joinPoint) throws NoSuchMethodException {
		clearPointCut();
	}
	
	public void clearPointCut(){
		log.debug("<<<<<< DataSourceAspect ClearPointCut : {}", DataSourceContextHolder.getDataSourceType());
		DataSourceContextHolder.clearDataSourceType();
	}

}