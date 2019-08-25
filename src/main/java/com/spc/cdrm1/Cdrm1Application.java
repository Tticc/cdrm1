package com.spc.cdrm1;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.xml.sax.InputSource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Cdrm1Application {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(Cdrm1Application.class);
		app.run(args);
		
		//SpringApplication.run(Cdrm1Application.class, args);
	}
	
	/**
	 * 容器测试。只看 ClassPathXmlApplicationContext
	 * @author Wen, Changying
	 * @date 2019年8月25日
	 */
	private static void test_context() {
		ApplicationContext ac1 = new ClassPathXmlApplicationContext("aop.xml","aop-jdbc.xml");
		//ApplicationContext ac2 = new FileSystemXmlApplicationContext("aop.xml");
		//ApplicationContext ac3 = new XmlWebApplicationContext("aop.xml");
		
		//Dao dao = (Dao)ac1.getBean("daoImpl");//根据名称获取Bean
		//dao.select();//执行Bean实例方法
		
		//关键方法路径：
		//AbstractApplicationContext#refresh();
		//默认使用的beanFactory：
		new DefaultListableBeanFactory();
	}

}

/**
 * https://www.cnblogs.com/dennyzhangdd/p/7688411.html
 * @author Wen, Changying
 * 2019年8月25日
 */
class myApplictionContext extends AbstractApplicationContext {

	public void doRefresh() {
		//synchronized (this.startupShutdownMonitor) {
		synchronized (this) {
			// Prepare this context for refreshing.
			// 准备刷新的上下文环境，例如对系统属性或者环境变量进行准备及验证。
			prepareRefresh();

			// Tell the subclass to refresh the internal bean factory.
			//启动子类的refreshBeanFactory方法.解析xml
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
			// ***************** important ************************************************************
			// ****************************************************************************************
			// ****************************************************************************************
			
			// AbstractRefreshableApplicationContext.refreshBeanFactory();
			// AbstractXmlApplicationContext.loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
				// 核心方法 loadBeanDefinitions(beanDefinitionReader);
			
			// 装载bean：XmlBeanDefinitionReader.doLoadBeanDefinitions(InputSource inputSource, Resource resource)

			// 装载完成后将bean注册到factory
			// DefaultListableBeanFactory.registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
			// 所以这是保存bean和beanName的map吗？BeanDefinition又是什么？
			//private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
			
			// ***************** important ************************************************************
			// ****************************************************************************************
			// ****************************************************************************************
			
			
			
			// Prepare the bean factory for use in this context.
			//为BeanFactory配置容器特性，例如类加载器、事件处理器等.
			prepareBeanFactory(beanFactory);

			try {
				// Allows post-processing of the bean factory in context subclasses.
				//设置BeanFactory的后置处理. 空方法，留给子类拓展用。 
				postProcessBeanFactory(beanFactory);

				// Invoke factory processors registered as beans in the context.
				//调用BeanFactory的后处理器, 这些后处理器是在Bean定义中向容器注册的.
				invokeBeanFactoryPostProcessors(beanFactory);

				// Register bean processors that intercept bean creation.
				//注册Bean的后处理器, 在Bean创建过程中调用.  
				registerBeanPostProcessors(beanFactory);

				// Initialize message source for this context.
				//初始化上下文中的消息源，即不同语言的消息体进行国际化处理  
				initMessageSource();

				// Initialize event multicaster for this context.
				//初始化ApplicationEventMulticaster bean,应用事件广播器
				initApplicationEventMulticaster();

				// Initialize other special beans in specific context subclasses.
				//初始化其它特殊的Bean， 空方法，留给子类拓展用。 
				onRefresh();

				// Check for listener beans and register them.
				//检查并向容器注册监听器Bean
				registerListeners();

				// Instantiate all remaining (non-lazy-init) singletons.
				//实例化所有剩余的(non-lazy-init) 单例Bean.
				finishBeanFactoryInitialization(beanFactory);

				// Last step: publish corresponding event.
				//发布容器事件, 结束refresh过程. 
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				//销毁已经创建的单例Bean, 以避免资源占用.
				destroyBeans();

				// Reset 'active' flag.
				//取消refresh操作, 重置active标志. 
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}

			finally {
				// Reset common introspection caches in Spring's core, since we
				// might not ever need metadata for singleton beans anymore...
				//重置Spring的核心缓存
				resetCommonCaches();
			}
		}
	}
	
	
	@Override
	protected void refreshBeanFactory() throws BeansException, IllegalStateException {}
	@Override
	protected void closeBeanFactory() {}
	@Override
	public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {return null;}
}


