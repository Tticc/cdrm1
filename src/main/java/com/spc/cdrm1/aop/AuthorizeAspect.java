package com.spc.cdrm1.aop;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spc.cdrm1.exception.CommonException;
import com.spc.cdrm1.service.CommonService;
import com.spc.cdrm1.util.CookieUtil;
import com.spc.cdrm1.util.enums.ResultEnum;
import com.spc.cdrm1.util.redisUtil.RedisUtil_Value;
import com.spc.cdrm1.vo.ResultVO;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AuthorizeAspect {
	
	@Value("${spc.token}")
	private String TOKEN;
	@Value("${spc.token_prefix}")
	private String TOKEN_PREFIX;
	@Autowired
	private RedisUtil_Value redisUtil_Value;
	@Autowired
	private CommonService commonService;
	
	@Pointcut("execution(public * com.spc.cdrm1.controller.*.*(..))" //切点为controller包下所有类，所有方法
			//除开VueController* 的 index* 方法
		    +"&& !execution(public * com.spc.cdrm1.controller.VueController*.index*(..))" 
			//和除开TestController* 的所有方法
		    +"&& !execution(public * com.spc.cdrm1.controller.TestController*.*(..))" 
			//和除开LoginController* 的所有方法
		    +"&& !execution(public * com.spc.cdrm1.controller.LoginController*.*(..))" 
			)
	public void verify() {}
	
	@Before("verify()")
	public void doVerify() throws CommonException {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = attr.getRequest();
		//检查 cookie，判断是否登录
		checkCookie(req, TOKEN);
	}
	private boolean checkCookie(HttpServletRequest req, String name) {
		ResultVO result = commonService.checkCookieIfAvailable(req, TOKEN);
		if(result.getCode() != ResultEnum.SUCCESS.getCode()) {
			String msg = "【登录校验】"+result.getMessage();
			log.warn(msg);
			throw new CommonException(msg);
		}
		return true;
	}
}
