package com.spc.cdrm1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spc.cdrm1.interceptor.BaseInterceptor;
import com.spc.other.rpcAnetty.simulateDubbo.MyFutureTask;
import com.spc.other.rpcAnetty.simulateDubbo.DubboService;

@Configuration
public class MyConfig implements WebMvcConfigurer{

	@Value("${spc.dubboHost:127.0.0.1}")
	private String dubboHost;
	@Value("${spc.dubboPort:8000}")
	private int dubboPort;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/**");
	}

	@Bean(name="dubboService")
	public DubboService dubboService() {
		System.out.println("\r\n\r\n");
		System.out.println("nettyRpc will connect to:"+dubboHost+":"+dubboPort);
		System.out.println("\r\n\r\n");
		DubboService.setHost(dubboHost);
		DubboService.setPort(dubboPort);
		return DubboService.getInstance().setMft(myFutureTask());
	}
	
	@Bean(name="myFutureTask")
	public MyFutureTask<Object> myFutureTask() {
		return  new MyFutureTask<Object>();
	}
}
