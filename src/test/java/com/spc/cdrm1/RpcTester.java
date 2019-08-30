package com.spc.cdrm1;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.event.ContextRefreshedEvent;

import com.spc.cdrm1.dubbomodule.rpcDemo.RpcFramework;
import com.spc.cdrm1.dubbomodule.rpcDemo.RpcService;
import com.spc.cdrm1.dubbomodule.rpcDemo.RpcServiceImpl;

public class RpcTester {
	@Test
	public void test_rpcs() throws Exception {
		RpcService rpcServiceImpl = new RpcServiceImpl();
		RpcFramework rpcFramework = new RpcFramework();
		//privide
//		new Thread(()-> {
//			try {
//				rpcFramework.provide(rpcServiceImpl, 8080);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}).start();
		//call
		Thread.sleep(2000);
		new Thread(()-> {
			RpcService service = rpcFramework.call(RpcService.class, "127.0.0.1", 8080);
			System.out.println("*************before rpc called**********************");
			System.out.println(service.sayHello("chad"));
			System.out.println("*************after rpc called**********************");
		}).start();
		Thread.sleep(5000);
	}

}
