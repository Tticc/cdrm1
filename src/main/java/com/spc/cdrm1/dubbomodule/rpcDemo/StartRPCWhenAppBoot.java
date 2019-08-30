package com.spc.cdrm1.dubbomodule.rpcDemo;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StartRPCWhenAppBoot implements ApplicationListener<ContextRefreshedEvent>{
	@Resource
	private RpcService rpcServiceImpl;
	@Resource
	private RpcFramework rpcFramework;
	
	// Autowired byType then byName
	@Autowired
	private ExecutorService threadPool;
	
	/**
	 * 启动一个 rpc service
	 */
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//启动rpc service
//		threadPool.execute(()->{
//		//new Thread(()-> {
//			try {
//				rpcFramework.provide(rpcServiceImpl, 8080);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		//}).start();
//		});
		
		// 调用
//		RpcService client = rpcFramework.call(RpcService.class, "localhost", 8000);
//		client.sayHello("chad");
	}
	
}
