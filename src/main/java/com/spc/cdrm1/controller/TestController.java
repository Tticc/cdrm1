package com.spc.cdrm1.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.spc.cdrm1.dubbomodule.client.DubboServiceClient;
import com.spc.cdrm1.dubbomodule.rpcDemo.RpcFramework;
import com.spc.cdrm1.dubbomodule.rpcDemo.RpcService;
import com.spc.cdrm1.service.ProductService;
import com.spc.cdrm1.util.CommonUtil;
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
	@Resource
	private RpcFramework rpcFramework;
	@Resource
	private ScheduledExecutorService scheduleThreadPool;
	@Resource
	private DubboServiceClient dubboServiceClient;

	/**
	 * Rpc 测试
	 * Rpc服务会在app启动时开启。{@linkplain com.spc.cdrm1.dubbomodule.rpcDemo.StartRPCWhenAppBoot StartRPCWhenAppBoot}
	 * @param name
	 * @return
	 * @throws Exception
	 * Aug 4, 2019
	 * @author cv
	 */
	@GetMapping("/hello/{name}")
	public ResultVO testRpc(@PathVariable String name) throws Exception {
		RpcService service = rpcFramework.call(RpcService.class, "127.0.0.1", 8080);
		Future<Object> f = threadPool.submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				//Thread.sleep(3000);
				return (service.sayHello(name));
			}
		});
		return ResultVOUtil.success(f.get());
		//FutureTask
//		FutureTask<Object> f = new FutureTask<Object>(new Callable<Object>() {
//			@Override
//			public Object call() throws Exception {
//				//Thread.sleep(3000);
//				return (service.sayHello(name));
//			}
//		});
//		new Thread(f).start();
//		return ResultVOUtil.success(f.get());
		

	}
	/**
	 * netty Rpc 测试
	 * @author Wen, Changying
	 * @param name
	 * @return
	 * @date 2019年8月24日
	 */
	@GetMapping("/nrpc/{name}")
	public ResultVO testNettyRpc(@PathVariable String name) {
		JSONObject params = new JSONObject();
		params.put("name", name);
		return (ResultVO)dubboServiceClient.getDubboService("sayHello", params);
	}
	/**
	 * netty Rpc 测试
	 * @author Wen, Changying
	 * @param name
	 * @return
	 * @date 2019年8月24日
	 */
	@GetMapping("/bye")
	public ResultVO sayBye() {
		JSONObject params = new JSONObject();
		return (ResultVO)dubboServiceClient.getDubboService("sayBye", params);
	}
	
	/**
	 * 定时器线程池测试
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * Aug 4, 2019
	 * @author cv
	 */
	@GetMapping("/spool")
	public void testScheduleThreadPool() throws InterruptedException, ExecutionException {
		Future<String> f = scheduleThreadPool.schedule(() -> {return "jooo";}, 8, TimeUnit.SECONDS);
		scheduleThreadPool.scheduleAtFixedRate(()->System.out.println(("schedule:"+ CommonUtil.getDateString())), 3, 5, TimeUnit.SECONDS);
		System.out.println(f.get());
		System.out.println(scheduleThreadPool.toString());
	}
	
	
	/**
	 * 线程池测试
	 * Aug 4, 2019
	 * @author cv
	 */
	@GetMapping("/pool")
	public void testThreadPool() {
		testExecute2();
		testSubmit();
		testSubmit2();
		System.out.println(threadPool.toString());
	}
	/**
	 * Callable，有返回数据，可以在try中获取数据。可以声明异常
	 * <p>{@code f.get()}声明了两个异常，所以需要try</p>
	 */
	private void testSubmit() {
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
			//String res = String.valueOf(f.get(5,TimeUnit.SECONDS));
			String res = String.valueOf(f.get());
			System.out.println(res);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//submit(Runnable task, T result);
	//Runnable作为入参，result是一个方法setReturn
	private void testSubmit2() {
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
	private String setReturn() {
		String aa = "return String";
		System.out.println(aa);
		return aa;
	}

	//execute
	private void testExecute() {
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
	//execute2
	private void testExecute2() {
		threadPool.execute(() -> {
			try {
				Thread.sleep(3000);
				System.out.println("thread was done");
			} catch (InterruptedException e) {}
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
