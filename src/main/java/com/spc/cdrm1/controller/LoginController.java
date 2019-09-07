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


/**
 * 登录测试。进入{@linkplain LoginController#login(HttpServletResponse, String, String) login}将信息保存。
 * 登录拦截在{@linkplain com.spc.cdrm1.aop.AuthorizeAspect#doVerify() doVerify}中实现
 * @author Wen, Changying
 * 2019年9月2日
 */
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
	
	
	/**
	 * 测试用login，判定用户名和密码，如果登录成功，将用户信息存入cookie和redis。
	 * @author Wen, Changying
	 * @param res
	 * @param name
	 * @param password
	 * @return
	 * @date 2019年9月2日
	 */
	@GetMapping("login")
	public ResultVO login(HttpServletResponse res, String name, String password) {
		//if(!("chad".equals(name) && "111".equals(password))) return ResultVOUtil.error("用户名或密码错误！");
		String value = name;
		value = "chad";
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
