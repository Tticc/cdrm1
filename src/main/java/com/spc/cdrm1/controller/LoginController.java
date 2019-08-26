package com.spc.cdrm1.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spc.cdrm1.service.ProductService;
import com.spc.cdrm1.util.CookieUtil;
import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.util.redisUtil.RedisUtil_Value;
import com.spc.cdrm1.vo.ResultVO;


@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Value("${spc.token}")
	private String TOKEN;
	@Value("${spc.token_prefix}")
	private String TOKEN_PREFIX;
	@Autowired
	private RedisUtil_Value redisUtil_Value;
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("login")
	public ResultVO login(HttpServletResponse res) {
		String value = "test";
		int time = 600;
		CookieUtil.setCookie(res, TOKEN, value, time);
		redisUtil_Value.setValue(String.format(TOKEN_PREFIX, value), "0", time);
		return ResultVOUtil.success();
	}

	@GetMapping("/404_notfound")
	public ResultVO methodNotFound(){
		return ResultVOUtil.error("请求路径错误！");
	}

}
