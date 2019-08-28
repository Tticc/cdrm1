package com.spc.other.rpcAnetty;

import com.spc.other.rpcAnetty.simulateDubbo.DubboServiceMe;

@DubboServiceMe
public interface Service {
	/**
	 * 可以通过注解的方式来实现 方法 和 service名字的绑定，用来初始化serviceMap
	 * @param name
	 * @return
	 * Aug 11, 2019
	 * @author cv
	 */
	public String sayHello(String name);
	public String sayBye();
}
