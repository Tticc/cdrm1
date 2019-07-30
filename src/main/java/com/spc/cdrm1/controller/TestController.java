package com.spc.cdrm1.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spc.cdrm1.service.ProductService;
import com.spc.cdrm1.util.CookieUtil;
import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.util.redisUtil.RedisUtil_Value;
import com.spc.cdrm1.vo.ResultVO;



@RestController
@RequestMapping("/test")
public class TestController {
	
	@Value("${spc.token}")
	private String TOKEN;
	@Value("${spc.token_prefix}")
	private String TOKEN_PREFIX;
	@Autowired
	private RedisUtil_Value redisUtil_Value;
	@Autowired
	private ProductService productService;
	@Resource
	private ExecutorService threadPool;

	@GetMapping("/pool")
	public void testThreadPool() {
		testExecute();
		testSubmit();
		testSubmit2();
		System.out.println(threadPool.toString());
	}
	/**
	 * Callable，有返回数据，可以在try中获取数据。可以声明异常
	 * <p>{@code f.get()}声明了两个异常，所以需要try</p>
	 */
	public void testSubmit() {
		Future<Object> f = threadPool.submit(new Callable<Object>() {
			@Override
			public Object call() throws InterruptedException{
				Thread.sleep(11000);
				return Thread.currentThread().getId()+"'s result";
			}
		});
		
		try {
			//do many thing here;
			Thread.sleep(4000);
			//do many thing here;
			String res = String.valueOf(f.get());
			System.out.println(res);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//submit(Runnable task, T result);
	//Runnable作为入参，result是一个方法setReturn
	public void testSubmit2() {
		Future<Object> f = threadPool.submit(new Runnable() {
			@Override
			public void run(){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {}
			}
		},setReturn());
		
		try {
			//do many thing here;
			Thread.sleep(8000);
			//do many thing here;
			String res = String.valueOf(f.get());
			System.out.println(res);
		}catch(Exception e) {}
	}
	public String setReturn() {
		String aa = "return String";
		System.out.println(aa);
		return aa;
	}

	//execute
	public void testExecute() {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("thread was done");
				} catch (InterruptedException e) {}
			}
		});
	}

	/*@GetMapping("login")
	public ResultVO login(HttpServletResponse res) {
		String value = "test";
		int time = 600;
		//CookieUtil.setCookie(res, TOKEN, value, time);
		//redisUtil_Value.setValue(String.format(TOKEN_PREFIX, value), "0", time);
		return ResultVOUtil.success();
	}*/

//	@GetMapping("/")
//	public String testswagger(HttpServletRequest request) {
//		return "index";
//	}

}
