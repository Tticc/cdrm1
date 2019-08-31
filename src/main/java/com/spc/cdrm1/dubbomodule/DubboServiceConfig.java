package com.spc.cdrm1.dubbomodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spc.cdrm1.dubbomodule.client.DubboServiceClient;
import com.spc.cdrm1.dubbomodule.client.MyFutureTask;
import com.spc.cdrm1.dubbomodule.server.DubboServiceProvider;
import com.spc.cdrm1.dubbomodule.server.ServiceMap;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DubboServiceConfig {

	@Value("${spc.server.dubboServicePath:com.spc.cdrm1.dubbomodule.service}")
	private String dubboServicePath;
	@Value("${spc.server.dubboServerPort:8000}")
	private int dubboServerPort;
	@Autowired
	private ServiceMap serviceMap;
	
	@Value("${spc.client.dubboHost:127.0.0.1}")
	private String dubboHost;
	@Value("${spc.client.dubboPort:8000}")
	private int dubboPort;

	// ***************************************** server ***********************************************
	@Bean(name="dubboServiceProvider")
	public DubboServiceProvider dubboServiceProvider() {
		DubboServiceProvider dsp = new DubboServiceProvider();
		try {
			// 初始化服务列表
			serviceMap.initServiceMap(dubboServicePath, true);
			// 启动 dubbo 服务器
			DubboServiceProvider.doProvide(100, dubboServerPort);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return dsp;
	}
	// ***************************************** server ***********************************************
	
	
	// ***************************************** client ***********************************************
	@Bean(name="dubboServiceClient")
	public DubboServiceClient dubboServiceClient() {
		System.out.println("\r\n\r\n");
		System.out.println("nettyRpc will connect to:"+dubboHost+":"+dubboPort);
		System.out.println("\r\n\r\n");
		DubboServiceClient.setHost(dubboHost);
		DubboServiceClient.setPort(dubboPort);
		return DubboServiceClient.getInstance();
	}
	
	/**
	 * MyFutureTask 应该是 DubboServiceClient 的一部分，不应该在外部初始化。
	 * @author Wen, Changying
	 * @return
	 * @date 2019年8月31日
	 */
//	@Bean(name="myFutureTask")
//	public MyFutureTask<Object> myFutureTask() {
//		return new MyFutureTask<Object>();
//	}
	// ***************************************** client ***********************************************
}
