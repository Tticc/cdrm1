package com.spc.cdrm1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spc.cdrm1.interceptor.BaseInterceptor;

@Configuration
public class MyConfig implements WebMvcConfigurer{

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/**");
	}
}
