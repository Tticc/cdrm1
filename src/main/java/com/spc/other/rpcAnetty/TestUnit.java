package com.spc.other.rpcAnetty;

import com.alibaba.fastjson.JSONObject;

public class TestUnit {
	public static void main(String[] args) throws Exception {
		test_myft();
	}
	
	private void test_call() {
		
	}
	private void test_jsontoObj() {
//		JSONObject obj =  (JSONObject) JSONObject.toJSON(new TestEntity());
//		String objStr = obj.toString();
//		System.out.println(objStr);
//		obj = (JSONObject) JSONObject.parse(objStr);
//		System.out.println(obj);
	}
	private static void test_myft() throws Exception {
		MyFutureTask<String> f = new MyFutureTask<String>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				f.set("result");
			}
		}).start();
		System.out.println(f.get());
	}
}
