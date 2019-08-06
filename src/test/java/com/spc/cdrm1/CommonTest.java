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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.junit.Test;

import net.bytebuddy.description.field.FieldDescription.InGenericShape;
/**
 * <ol>
 * <li>测试 ConcurrentHashMap</li>
 * <li>测试 Integer 和 int 的打包解包转换和存储位置</li>
 * <li>测试 hashcode</li>
 * <li>测试父类，子类变量</li>
 * <li>测试 FutureTask线程回调</li>
 * <li>测试java大数字</li>
 * <li>测试StringBuffer的完全替换</li>
 * <li>测试 Type和Class</li>
 * <li>测试对象类型数组的初始化</li>
 * <li>测试break point，未测出来</li>
 * <li>线程的 run 和 start</li>
 * <li>抽象类测试</li>
 * <li>Interface 测试</li>
 * </ol>
 * Aug 6, 2019
 */
@SuppressWarnings("unused")
public class CommonTest {

	// 测试 ConcurrentHashMap 2019-08-06
	@Test
	public void test_ConcurrentMap() {
		int HASH_BITS = 0x7fffffff;
		System.out.println(1&HASH_BITS);
		Map<String, String> chm = new ConcurrentHashMap<String, String>();
	}
	//测试 Integer 和 int 的打包解包转换和存储位置
	@Test
	public void test_push() {
		Integer i1 = new Integer(9);
		Integer i2 = new Integer(9);
		System.out.println(i1==i2);
		System.out.println(i1.equals(i2));

		int i3 = new Integer(14);
		int i4 = new Integer(14);
		System.out.println(i3 == i4);
		//System.out.println(i3.equals(i4));
		
		Integer i5 = new Integer(16);
		Integer i6 = new Integer(16);
		System.out.println(i5==i6);
		System.out.println(i5.equals(i6));
		
	    Integer a = 128;
	    Integer b = 128;
	    System.out.println(a == b);
	}
	// 测试 hashcode
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
	
	// 测试父类，子类变量
	@Test
	public void test_super_sub_key() {
		new Sub().priKey();
	}
	// 测试function，失败！
//	private <T, R> R test_fun(Function<? extends T, ? extends R> f) {
//		//return (T t) -> f.apply(t);
//	}
	
	// 测试 FutureTask线程回调
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
	
	// 测试java大数字
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
	// 测试StringBuffer的完全替换
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
	// 测试 Type和Class
	@Test
	public void test_typeAndClass(){
		System.out.println(Long.TYPE);
		System.out.println(long.class);
		System.out.println(Long.class);
	}
	// 测试对象类型数组的初始化
	@Test
	public void tetArray() {
		Integer[] str = new Integer[10];
		System.out.println(str[5]);
	}
	
	// 测试break point，未测出来
	@Test
	public void testBreak() {
		breakPoint:
		for(int j = 0; j < 2; j ++) {
			for(int i = 0; i < 10; i++) {
				if(i>5) {
					break;
				}
				System.out.println(i);
			}
		}
		System.out.println("end");
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
/**
 * 抽象类测试
 * Aug 6, 2019
 */
abstract class Super{
	private String Key_user = "key super";
	Super(String name){}
	void priKey() {
		System.out.println(this.Key_user);//输出的是 key super，而不是子类的key sub 1
	}
	// protected方法可以被子类继承，并且能够在子类中重写并扩展访问级别
	protected void protectedFunc() {
		System.out.println("super Name");
	}
	// 私有方法不会被子类继承
	private void privateFunc() {}
}
class Sub extends Super{
	//父类没有无参构造器，子类构造器必须要调用父类的有参构造器。
	String Key_user = "sub key 1";
	Sub(){super("default");}
	Sub(String name) {super(name);}
	static synchronized String test_sync(){
		try {
			Thread.sleep(5000);

			return "helloooo";
		} catch (InterruptedException e) {}
		return "exception throwed";
	}

	@Override
	public void protectedFunc() {
		super.protectedFunc();
	}
}

/**
 * Interface 测试
 * Aug 6, 2019
 */
interface Innt{
	/**
	 * 接口定义不抛出异常，那么实现类的实现方法也不能抛出异常，必需将异常捕捉。
	 */
	public void runn();
	//final void tt();
	default void runnnn() {
		
	}
	default void vun3() {
		
	}
	static void namenn() {
		System.out.println("interface");
	}
}
class SubInnt implements Innt{
	@Override
	public void runn() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		
	}
	
}

