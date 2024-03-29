package com.spc.cdrm1.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.spc.cdrm1.util.enums.ResultEnum;

import lombok.Data;

@Data
public class ResultVO<T> implements Serializable {
	private static final long serialVersionUID = 9172171625353985099L;

	/**
	 * 错误代码
	 */
	private int code;
	
	/**
	 * 提示信息
	 */
	private String message;
	
	/**
	 * 具体数据
	 */
	private T data;
	
	public boolean isSuccess() {
		return this.getCode() == ResultEnum.SUCCESS.getCode();
	}
	@Override
	public String toString() {
		
		return "{code="+code+",message="+message+",date="+data+"}";
	}
	
}
