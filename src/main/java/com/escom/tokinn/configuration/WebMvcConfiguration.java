package com.escom.tokinn.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.escom.tokinn.component.SecurityInterceptor;

@SuppressWarnings("deprecation")
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
	@Autowired
	@Qualifier("securityInterceptor")
	private SecurityInterceptor securityInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor);
	}
}
