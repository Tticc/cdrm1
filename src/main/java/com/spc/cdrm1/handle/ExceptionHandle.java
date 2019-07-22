package com.spc.cdrm1.handle;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.spc.cdrm1.exception.CustomException;
import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.util.enums.ExceptionEnum;
import com.spc.cdrm1.vo.ResultVO;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {

	public void test() {
		log.info("jie");
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultVO exceptionGet(Exception e) {
		log.error("\r\nerror:", e);
        /**
         * 如果抛出的自定义异常
         */
        if(e instanceof CustomException){
            return ResultVOUtil.error(((CustomException)e).getCode(), ((CustomException)e).getMessage());
        }
		return ResultVOUtil.error(1,"execption message:"+e.getMessage());
	}

    /*@ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResultVO handleResponseBankException() {
    	return ResultVOUtil.error(ExceptionEnum.NO_METHOD_ERROR);
    }*/
}
