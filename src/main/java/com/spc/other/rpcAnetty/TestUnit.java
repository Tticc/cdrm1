package com.spc.other.rpcAnetty;

import com.alibaba.fastjson.JSONObject;

public class TestUnit {
	public void main(String[] args) throws Exception {
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
	private void test_myft() throws Exception {
		MyFutureTask<String> f = new MyFutureTask<String>();
		f.set("result");
		System.out.println(f.get());
	}
}
