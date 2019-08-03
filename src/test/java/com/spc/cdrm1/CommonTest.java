package com.spc.cdrm1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.junit.Test;

@SuppressWarnings("unused")
public class CommonTest {
	
	@Test
	public void test_hass() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("aa", "aaa");
		hashMap.get("kk");
		"abc".hashCode();
		Map<String, Object> treeMap = new TreeMap<String, Object>();
		for(int i = 528 ; i < 568; i++) {
			//System.out.println(5&i);
		}
		treeMap.put("null", null);
		System.out.println(treeMap.get("null"));
//		Comparable k = (Comparable) "abc";
//		System.out.println(k.compareTo(null));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test_Function() {
		new ArrayList().stream().sorted().forEach((e) -> System.out.println("jdios"));
	}
	
//	private <T, R> R test_fun(Function<? extends T, ? extends R> f) {
//		//return (T t) -> f.apply(t);
//	}
	
	@Test
	public void testSync() throws InterruptedException, ExecutionException {
//		FutureTask ft = new FutureTask(new Callable() {
//
//			@Override
//			public Object call() throws Exception {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//		});
//		new Thread(ft).start();
//		ft.get();
	}
	
	@Test
	public void test_BigInteger() {
		BigInteger bigInteger = new BigInteger("999999999999999999999999999999910001");
		System.out.println(bigInteger);
		BigDecimal bigDecimal = new BigDecimal("1232123.23213");
		System.out.println(bigDecimal);
		float[] in = new float[11];
		System.out.println(in[3]);
		
		List<String> list = Arrays.asList("a","b","c");
		//list.add("d");
		//list.remove("b");
		list.clear();
		System.out.println(list);
	}
	@Test
	public void testSb() {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.replace(0, sbBuffer.length(), "123456789");
		System.out.println(sbBuffer.toString());
		sbBuffer.replace(0, sbBuffer.length(), "1234");
		System.out.println(sbBuffer.toString());
		sbBuffer.replace(0, sbBuffer.length(), "123456789abcdefghij");
		System.out.println(sbBuffer.toString());
	}
	@Test
	public void testArraysAdd() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class.forName("com.spc.cdrm1.CommonTest").newInstance();
	}
	@Test
	public void test_typeAndClass(){
		System.out.println(Long.TYPE);
		System.out.println(long.class);
		System.out.println(Long.class);
	}
	@Test
	public void tetArray() {
		Integer[] str = new Integer[10];
		System.out.println(str[5]);
	}
	
	@Test
	public void testBreak() {
		for(int j = 0; j < 2; j ++) {
		for(int i = 0; i < 10; i++) {
			if(i>5) {
				break;
			}
			System.out.println(i);
		}
		}
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 线程的 run 和 start
	 * <ol>
	 * <li>run 是线程执行的内容</li>
	 * <li>start 是开启新线程的方法</li>
	 * </ol>
	 * @author Wen, Changying
	 * @throws InterruptedException
	 * @date 2019年7月21日
	 */
	@Test
	public void test_thread() throws InterruptedException {
		new Thread(
		new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3555);
					System.out.println("after 5 sec");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
		System.out.println("main thread");
		Thread.sleep(5555);
		System.out.println("main thread end");
	}
}
class Super{
	Super(String name){}
}
class Sub extends Super{
	//父类没有无参构造器，子类构造器必须要调用父类的有参构造器。
	Sub(){super("default");}
	Sub(String name) {super(name);}
	static synchronized String test_sync(){
		try {
			Thread.sleep(5000);

			return "helloooo";
		} catch (InterruptedException e) {}
		return "exception throwed";
	}
}
interface Innt{
	/**
	 * 接口定义不抛出异常，那么实现类的实现方法也不能抛出异常，必需将异常捕捉。
	 */
	public void runn();
	//final void tt();
}
class SubInnt implements Innt{
	@Override
	public void runn() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
	}
	
}

