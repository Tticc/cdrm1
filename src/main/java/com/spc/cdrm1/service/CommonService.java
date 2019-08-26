package com.spc.cdrm1.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.spc.cdrm1.exception.CommonException;
import com.spc.cdrm1.util.CookieUtil;
import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.util.redisUtil.RedisUtil_Value;
import com.spc.cdrm1.vo.ResultVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("commonService")
public class CommonService {
	
	@Value("${spc.token}")
	private String TOKEN;
	@Value("${spc.token_prefix}")
	private String TOKEN_PREFIX;
	@Autowired
	private RedisUtil_Value redisUtil_Value;

	public ResultVO checkCookieIfAvailable(HttpServletRequest req, String name) {
		Cookie cookie = CookieUtil.getCookie(req, name);
		if (cookie == null) {
			return ResultVOUtil.error(String.format("Cookie中查不到%s", name));
		}/*
		System.out.println(cookie.getDomain());
		System.out.println(cookie.getMaxAge());
		System.out.println(cookie.getPath());*/
		String tokenValue = (String) redisUtil_Value.getValue(String.format(TOKEN_PREFIX, cookie.getValue()));
		if(StringUtils.isEmpty(tokenValue)) {
			return ResultVOUtil.error(String.format("Redis中查不到%s", name));
		}
		return ResultVOUtil.success();
	}
}
