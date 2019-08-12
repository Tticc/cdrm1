package com.spc.other.rpcAnetty;

import java.lang.reflect.Method;
import java.util.Map;

import javax.activation.MailcapCommandMap;

import com.alibaba.fastjson.JSONObject;


public class ServiceMap {
	public static Map<String, Method> serviceMap;

	public static void main(String[] args) {
		JSONObject obj =  (JSONObject) JSONObject.toJSON(new TestEntity());
		String objStr = obj.toString();
		System.out.println(objStr);
		obj = (JSONObject) JSONObject.parse(objStr);
		System.out.println(obj);
	}
	/**
	 * 类加载时初始化serviceMap
	 */
	static {
		try {
			Method m = ServiceImpl.class.getMethod("sayHello", String.class);
			serviceMap.put("sayHello", m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
