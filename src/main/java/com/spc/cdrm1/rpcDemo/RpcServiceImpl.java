package com.spc.cdrm1.rpcDemo;
import org.springframework.stereotype.Component;

@Component("rpcServiceImpl")
public class RpcServiceImpl implements RpcService{
	@Override
	public String sayHello(String name) {
		System.out.println("sayHello was called!");
		return "Hello "+name;
	}
}
