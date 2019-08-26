package com.spc.other.rpcAnetty;

public class ServiceImpl implements Service{

	@Override
	public String sayHello(String name) {
		return "hello "+name;
	}

	@Override
	public String sayBye() {
		return "bye, client";
	}

}
