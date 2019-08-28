package com.spc.other.rpcAnetty.simulateDubbo;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import com.spc.other.rpcAnetty.ServiceImpl;


public class ServiceMap {
	public static Map<String, Method> serviceMap = new HashMap<String, Method>();

	public static void main(String[] args) {
		List<String> list = getClazzName("com.spc.other.rpcAnetty.simulateDubbo",true);
		List<Class<?>> listCla = getAllClass(list);
		Iterator<Class<?>> ite = listCla.iterator();
		while(ite.hasNext()) {
			System.out.println(ite.next());
		}
	}
	/**
	 * 类加载时初始化serviceMap
	 */
	static {
		try {
			Method m = ServiceImpl.class.getMethod("sayHello", String.class);
			serviceMap.put("sayHello", m);
			Method bye = ServiceImpl.class.getMethod("sayBye");
			serviceMap.put("sayBye", bye);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static List<Class<?>> getAllClass(List<String> classFullPath){
		List<Class<?>> result = new LinkedList<Class<?>>();
		Iterator<String> ite = classFullPath.iterator();
		while(ite.hasNext()) {
			try {
				result.add(Class.forName(ite.next()));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 查找包下的所有类的名字
	 * @author Wen, Changying
	 * @param packageName
	 * @param recursive
	 * @return
	 * @date 2019年8月28日
	 */
	public static List<String> getClazzName(String packageName, boolean recursive) {
		List<String> result = new LinkedList<String>();
		String packageDirName = packageName.replaceAll("\\.", "/");
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()){
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					// System.out.println("syso -- file类型");
					result.addAll(getAllClassNameByFile(new File(url.getPath()), recursive));
				}else if("jar".equals(protocol)){
					JarFile jarFile = null;
					try{
						jarFile = ((java.net.JarURLConnection) url.openConnection()).getJarFile();
					} catch(Exception e){
						e.printStackTrace();
					}
					if(jarFile != null) {
						result.addAll(getAllClassNameByJar(jarFile, packageName, recursive));
					}
				}
			}
		}catch(Exception e) {
			
		}
		return result;
	}
	/**
	 * 递归获取所有class文件的名字
	 * @author Wen, Changying
	 * @param file
	 * @param recursive
	 * @return
	 * @date 2019年8月28日
	 */
	public static List<String> getAllClassNameByFile(File file, boolean recursive) {
		List<String> result =  new LinkedList<>();
		if(!file.exists()) return result;
		if(file.isFile()) {
			String path = file.getPath();
			if(path.endsWith(".class")) {
				path = path.replace(".class", "");
				String class_file_prefix = File.separator + "classes"  + File.separator;
				String clazzName = path.substring(path.indexOf(class_file_prefix) + class_file_prefix.length())
						.replace(File.separator, ".");
				if(-1 == clazzName.indexOf("$")) result.add(clazzName);
			}
			return result;
		}else {
			File[] listFiles = file.listFiles();
			if(listFiles != null && listFiles.length > 0) {
				for (File f : listFiles) {
					if(recursive) {
						result.addAll(getAllClassNameByFile(f, recursive));
					}else {
						if(f.isFile()){
							String path = f.getPath();
							if(path.endsWith(".class")) {
								path = path.replace(".class", "");
								String class_file_prefix = File.separator + "classes"  + File.separator;
								String clazzName = path.substring(path.indexOf(class_file_prefix) + class_file_prefix.length())
										.replace(File.separator, ".");
								if(-1 == clazzName.indexOf("$")) result.add(clazzName);
							}
						}
					}
					
				}
			}
		}
		return result;
	}
	
	/**
	 * 递归获取jar所有class文件的名字
	 * @author Wen, Changying
	 * @param jarFile
	 * @param packageName
	 * @param flag
	 * @return
	 * @date 2019年8月28日
	 */
	private static List<String> getAllClassNameByJar(JarFile jarFile, String packageName, boolean flag) {
		List<String> result =  new LinkedList<>();
		Enumeration<JarEntry> entries = jarFile.entries();
		while(entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement();
			String name = jarEntry.getName();
			// 判断是不是class文件
			if(name.endsWith(".class")) {
				name = name.replace(".class", "").replace("/", ".");
				if(flag) {
					// 如果要子包的文件,那么就只要开头相同且不是内部类就ok
					if(name.startsWith(packageName) && -1 == name.indexOf("$")) {
						result.add(name);
					}
				}else {
					// 如果不要子包的文件,那么就必须保证最后一个"."之前的字符串和包名一样且不是内部类
					if(packageName.equals(name.substring(0, name.lastIndexOf("."))) && -1 == name.indexOf("$")) {
						result.add(name);
					}
				}
			}
		}
		return result;
	}
}
