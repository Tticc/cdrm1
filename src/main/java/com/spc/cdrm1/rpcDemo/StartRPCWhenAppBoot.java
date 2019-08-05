package com.spc.cdrm1.rpcDemo;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;
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
	@Resource
	private ExecutorService threadPool;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//启动rpc service
		threadPool.execute(()->{
		//new Thread(()-> {
			try {
				rpcFramework.provide(rpcServiceImpl, 8080);
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}).start();
		});
	}
}
