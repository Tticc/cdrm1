package com.spc.cdrm1;

import java.util.regex.Pattern;

import org.junit.Test;

@SuppressWarnings("unused")
public class CommonTest {
	
	
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



