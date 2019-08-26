package com.spc.other.rpcAnetty;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MailcapCommandMap;

import com.alibaba.fastjson.JSONObject;


public class ServiceMap {
	public static Map<String, Method> serviceMap = new HashMap<String, Method>();

	public static void main(String[] args) {
		
	}
	/**
	 * 类加载时初始化serviceMap
	 */
	static {
		try {
			Method m = ServiceImpl.class.getMethod("sayHello", String.class);
			serviceMap.put("sayHello", m);
			Method bye = ServiceImpl.class.getMethod("sayBye");
			serviceMap.put("sayBye", bye);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
