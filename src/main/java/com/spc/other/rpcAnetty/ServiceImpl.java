package com.spc.other.rpcAnetty;

public class ServiceImpl implements Service{

	@Override
	public String sayHello(String name) {
		return "hello "+name;
	}

}
