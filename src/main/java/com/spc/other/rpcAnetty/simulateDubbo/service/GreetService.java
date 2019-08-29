package com.spc.other.rpcAnetty.simulateDubbo.service;

import com.spc.other.rpcAnetty.simulateDubbo.DubboServiceMe;

@DubboServiceMe
public class GreetService {

	public String sayHello(String name) {
		return "Hello " + name;
	}
	
	public String sayBye() {
		return "bye, client";
	}
}
