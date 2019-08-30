package com.spc.cdrm1.dubbomodule.service;

import com.spc.cdrm1.dubbomodule.server.DubboServiceMe;

@DubboServiceMe
public class GreetService {

	
	public String sayHello(String name) {
		return "Hello " + name;
	}
	
	public String sayBye() {
		return "bye, client";
	}
}
