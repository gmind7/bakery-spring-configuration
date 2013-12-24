package com.gmind7.bakery.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
 
public class CorsFilter extends OncePerRequestFilter {
 
	protected Logger log = LoggerFactory.getLogger(CorsFilter.class);
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            log.debug("<<<<<<<<<< CorsFilter : CORS pre-flight request");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, X-Requested-With, Content-Type, Accept, Orgin");
            response.addHeader("Access-Control-Expose-Headers", "Date, Link, X-Expiration-Time, X-RateLimit-Limit, X-RateLimit-Remaining, X-OAuth-Scopes, X-Accepted-OAuth-Scope");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Max-Age", "86400");
        }
        filterChain.doFilter(request, response);
    } 
}
 
// example web.xml configuration
 
/*
  <filter>
    <filter-name>cors</filter-name>
    <filter-class>com.ncsoft.platform.infra.web.rest.filter.CorsFilter</filter-class>
  </filter>
  
  <filter-mapping>
    <filter-name>cors</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
*/
