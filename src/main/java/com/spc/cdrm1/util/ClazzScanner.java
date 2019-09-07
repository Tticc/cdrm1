package com.spc.cdrm1.util;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;

import org.springframework.stereotype.Component;

/**
 * 网上抄的。类扫描器
 * @author Wen, Changying
 * 2019年9月5日
 */
@Component
public class ClazzScanner {

	private static final String CLASS_SUFFIX = ".class";
	private static final String CLASS_FILE_PREFIX = File.separator + "classes"  + File.separator;
	private static final String PACKAGE_SEPARATOR = ".";
	
	public static List<Class<?>> getAllClass(String packageName, boolean recursive){
		List<Class<?>> result = new LinkedList<Class<?>>();
		List<String> classFullPath = getClazzName(packageName, recursive);
		if(classFullPath == null || classFullPath.size() == 0) return null;
		
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
	
	public static List<String> getClazzName(String packageName, boolean recursive) {
		List<String> result = new LinkedList<String>();
		// 打包部署到服务器时出错了，估计是这里有问题。
		String packageDirName = packageName.replaceAll("\\.", "/");// Matcher.quoteReplacement(File.separator)
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
	
	private static List<String> getAllClassNameByFile(File file, boolean recursive) {
		List<String> result =  new LinkedList<>();
		if(!file.exists()) return result;
		if(file.isFile()) {
			String path = file.getPath();
			if(path.endsWith(CLASS_SUFFIX)) {
				path = path.replace(CLASS_SUFFIX, "");
				String clazzName = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length())
						.replace(File.separator, PACKAGE_SEPARATOR);
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
							if(path.endsWith(CLASS_SUFFIX)) {
								path = path.replace(CLASS_SUFFIX, "");
								String clazzName = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length())
										.replace(File.separator, PACKAGE_SEPARATOR);
								if(-1 == clazzName.indexOf("$")) result.add(clazzName);
							}
						}
					}
					
				}
			}
		}
		return result;
	}
	
	private static List<String> getAllClassNameByJar(JarFile jarFile, String packageName, boolean flag) {
		List<String> result =  new LinkedList<>();
		Enumeration<JarEntry> entries = jarFile.entries();
		while(entries.hasMoreElements()) {
			JarEntry jarEntry = entries.nextElement();
			String name = jarEntry.getName();
			// 判断是不是class文件
			if(name.endsWith(CLASS_SUFFIX)) {
				name = name.replace(CLASS_SUFFIX, "").replace("/", ".");
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
