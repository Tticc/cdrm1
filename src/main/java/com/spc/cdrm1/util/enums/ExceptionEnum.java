package com.spc.cdrm1.util.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum implements MyEnum{

	HTTP_METHOD(100, "请求方式不正确"),

	NO_METHOD_ERROR(404, "未找到资源"),
	
	UNKNOW_ERROR(500, "未知异常"),
	;

    private int code;

    private String message;
    
    ExceptionEnum(int code, String message){
        this.code = code;
        this.message = message;    	
    }
}
