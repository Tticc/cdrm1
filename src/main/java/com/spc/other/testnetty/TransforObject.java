package com.spc.other.testnetty;

import java.io.Serializable;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class TransforObject<T> extends TransforObjectSuper implements TransforInterface, Serializable{
	private static final long serialVersionUID = 1320123451767766661L;
	private String name;
	public void doSomething() {
		System.out.println("do something");
	}
	private T data;
}
