# spring-configuration-sample [![Build Status](https://travis-ci.org/spring-projects/spring-boot.png?branch=master)](https://travis-ci.org/spring-projects/spring-boot)

Spring Framwork을 이용한 웹, 배치, 데몬 환경에 대한 설정을 모듈 별로 재 구성한 프로젝트 입니다.
각 모듈은 기 운영중인 Artifactory Server로 업로드 한 후 각 프로젝트 환경 특성에 맞게 dependencies 하여 사용 할 목적으로 개발 되었습니다.

### spring-application-config

@ComponentScan, AOP, Async, Scheduling, ErrorMessageFactory 설정을 구성 합니다.

```java
@Configuration
@ComponentScan(basePackages = "com.gmind7", excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class AppConfig {

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true) // <aop:aspectj-autoproxy>
public class AspectConfig {

@Configuration
@EnableAsync(mode=AdviceMode.PROXY) // <task:*>
@EnableScheduling
public class TaskConfig implements SchedulingConfigurer, AsyncConfigurer {

```

### spring-batch-config

스프링 배치를 위한 기본 설정이 셋팅 되어 있으며 BatchConfigurerAdapter 상속 받은 후 DataSource, PlatformTransactionManager만 구현 하여 프로젝트를 진행 합니다.

```java
@EnableBatchProcessing
public abstract class BatchConfigurerAdapter implements BatchConfigurer {

@Bean(destroyMethod = "close")
public abstract DataSource getBatchDataSource();
	
public abstract PlatformTransactionManager getTransactionManager();
```

### spring-web-api-config

Rest API 웹 서버 구현을 위한 환경 설정 으로 Hateoas, CorsFilter, Global Exception..을 지원 합니다. 

```java
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer 

@Configuration
@EnableHypermediaSupport
@EnableEntityLinks
@EnableWebMvc
@ComponentScan(basePackages = "com.gmind7", useDefaultFilters = false, includeFilters = {@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)})
public class WebRestConfig extends WebMvcConfigurerAdapter {
```

### spring-web-mvc-config

페이지 구성이 있는 웹 서버 구현을 위한 환경 설정 templateEngine은 thymeleaf를 지원 합니다.

```java
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.gmind7", useDefaultFilters = false, includeFilters = @ComponentScan.Filter(Controller.class))
```

### spring-web-support

웹 프로젝트에 부가적으로 필요한 기능을 설정 한 프로젝트로 restTemplate, Jsonp, Validation Type..을 지원 합니다.

```java
@Bean(name="restTemplate")
public RestTemplate restTemplate() {

public class RestTemplateErrorHandler implements ResponseErrorHandler {

public class MappingJackson2JsonpView extends MappingJackson2JsonView {

public class UserLocationHandlerInterceptor implements HandlerInterceptor {
```

### test-spring-batch-config

배치 프로젝트는 spring-application-config, spring-batch-config 모듈이 필요하며 두개의 method만 추가 구현 후 진행 합니다.

```java
compile project(":spring-application-config") 
compile project(":spring-batch-config")
```

```java
@Configuration
@EnableJpaRepositories("com.gmind7")
@EnableTransactionManagement(proxyTargetClass = true, order = 2)
public class BatchConfig extends BatchConfigurerAdapter {
  @Override
	public DataSource getBatchDataSource() {
		return dataSource();
	}

	@Override
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager();
	}
}
```

### test-spring-boot-config

데몬 프로젝트는 spring-application-config 모듈이 필요하며 run method를 구현 합니다.

```java
compile project(":spring-application-config")

@Configuration
@EnableAutoConfiguration
@Import(AppConfig.class)
public class SpringBootRunner implements CommandLineRunner {
  @Override
	public void run(String... arg0) throws Exception {
		boot.getHelloWorld();
	}
}
```

### test-spring-web-config

웹 프로젝트는 spring-application-config, spring-web-mvc-config, spring-web-support 모듈이 필요하며 다른 구현 내용은 없습니다. (Rest API 서버인 경우 : spring-web-api-config로 변경)

```java
compile project(":spring-application-config")
compile project(":spring-web-mvc-config") 
compile project(":spring-web-support") 
```
