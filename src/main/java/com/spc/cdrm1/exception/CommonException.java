package com.spc.cdrm1.exception;

import lombok.Data;

@Data
public class CommonException extends RuntimeException implements CustomException{

	public CommonException() {}
	public CommonException(String message) {
		this(message,0);
	}
	public CommonException(String message,int code ) {
		this.message = message;
		this.code = code;
	}
	private int code;
	private String message;

}
