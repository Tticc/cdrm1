package com.spc.cdrm1.dubbomodule.service;

import org.springframework.stereotype.Component;

import com.spc.cdrm1.dubbomodule.server.DubboServiceMe;

@DubboServiceMe
@Component
public class GreetService {

	
	public String sayHello(String name) {
		return "Hello " + name;
	}
	
	public String sayBye() {
		return "bye, client";
	}
}
