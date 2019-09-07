package com.spc.cdrm1.util;

import java.util.LinkedList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * 依赖于Spring的类扫描器。与 {@linkplain ClazzScanner} 不同的是，这个是类扫描器依赖于Spring
 * <p>
 * 之所以添加这个类，是因为发现 {@linkplain ClazzScanner} 打包部署到weblogic服务器时类扫描器不起作用。
 * 因为weblogic部署时会将classes文件夹下的所有文件打包成一个jar文件。所以 {@linkplain ClazzScanner} 通过
 * 绝对路径获取class的方式无法拿到class文件，所以无法使用。而spring的类扫描器无论在tomcat或weblogic或其他
 * 服务器都能正常使用，所以尝试使用这个类扫描器
 * </p>
 * @author Wen, Changying
 * 2019年9月6日
 */
public class ClazzScannerWithinSpring {
	protected static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
	
	/**
	 * 根据包名，获取特定包下的所有class。
	 * @author Wen, Changying
	 * @param scanPackageArr
	 * @return
	 * @throws Exception
	 * @date 2019年9月6日
	 */
	public static List<Class<?>> loadAllClass(String ...scanPackageArr) throws Exception{
		List<Class<?>> cls = new LinkedList<Class<?>>();
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
	    for (String basePackage : scanPackageArr) {
	    	if ("null".equals(String.valueOf(basePackage)) || "".equals(basePackage)) {
	    		continue;
	    	}
	    	String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
	                ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
	    	try {
	    		Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
	    		for (Resource resource : resources) {
	    			cls.add(loadClassMethod(metadataReaderFactory, resource));
	    		}
	    	}catch(Exception e){
	    		throw e;
	    	}
	    }
    	return cls;
	}
	
	/**
	 * 通过 resource 获取类全名，根据全名转载类，获得class。
	 * @author Wen, Changying
	 * @param metadataReaderFactory
	 * @param resource
	 * @return
	 * @throws Exception
	 * @date 2019年9月6日
	 */
	private static Class<?> loadClassMethod(MetadataReaderFactory metadataReaderFactory, Resource resource) throws Exception {
		try {
			if(resource.isReadable()) {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				if (metadataReader != null) {
					String className = metadataReader.getClassMetadata().getClassName();
					return Class.forName(className);
				}
			}
		}catch (Exception e) {
			throw e;
		}
		return null;
	}
}
