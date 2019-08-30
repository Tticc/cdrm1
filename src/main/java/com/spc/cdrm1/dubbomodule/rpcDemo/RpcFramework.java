package com.spc.cdrm1.dubbomodule.rpcDemo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;


@Component("rpcFramework")
public class RpcFramework {
	@Resource
	private ExecutorService threadPool;

	public void provide(final Object service, int port) throws IOException {
		if(service == null) 
			throw new IllegalArgumentException("service instance == null");
		if(port <=0 || port > 65536) 
			throw new IllegalArgumentException("invalid port "+port);
		System.out.println("service "+service.getClass().getName()+" provided on port "+port);
		ServerSocket serverSocket = new ServerSocket(port);
		for(;;) {
			try {
				final Socket socket = serverSocket.accept();
				threadPool.execute(()->{
				//new Thread(() -> {
					try {
						try {
							ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
							try {
								String methodName = in.readUTF();
								Class<?>[] paramsTypes = (Class<?>[]) in.readObject();
								Object[] arguments = (Object[]) in.readObject();
								ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
								try {
									Method method = service.getClass().getMethod(methodName, paramsTypes);
									Object result = method.invoke(service, arguments);
									out.writeObject(result);
								}catch(Throwable t) {
									out.writeObject(t);
								}finally {
									out.close();
								}
							}finally {
								in.close();
							}
						}finally {
							socket.close();
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				});
				//}).start();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public <T> T call(final Class<T> interfaceClass,final String host, final int port) {
		if(interfaceClass == null)
			throw new IllegalArgumentException("interfaceClass == null");
		if(!interfaceClass.isInterface())
			throw new IllegalArgumentException("argument "+interfaceClass.getName()+" must be a interface");
		if(host == null || host.length() == 0)
			throw new IllegalArgumentException("host == null");
		if(port <=0 || port > 65536) 
			throw new IllegalArgumentException("invalid port "+port);
		System.out.println("call service "+interfaceClass.getName()+" from "+host+":" + port);
		
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), 
				new Class<?>[] {interfaceClass}, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Socket socket = new Socket(host,port);
						try {
							ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
							try {
								out.writeUTF(method.getName());
								out.writeObject(method.getParameterTypes());
								out.writeObject(args);
								ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
								try {
									Object result = in.readObject();
									if(result instanceof Throwable)
										throw (Throwable)result;
									return result;
								}finally {
									in.close();
								}
							}finally {
								out.close();
							}
						}finally {
							socket.close();
						}
					}
				});
	}
}
