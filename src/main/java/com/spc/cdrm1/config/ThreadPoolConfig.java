package com.spc.cdrm1.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfig {

	@Bean(name = "threadPool")
	public ExecutorService threadPool() {
		ExecutorService execCached = Executors.newCachedThreadPool();
		ExecutorService execFixed = Executors.newFixedThreadPool(10);
		ScheduledExecutorService execSchedule = Executors.newScheduledThreadPool(10);
		ThreadPoolExecutor execCus = new ThreadPoolExecutor(20, 30, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		return execFixed;
	}

	@Bean(name = "scheduleThreadPool")
	public ScheduledExecutorService scheduleThreadPool() {
		ExecutorService execCached = Executors.newCachedThreadPool();
		ExecutorService execFixed = Executors.newFixedThreadPool(10);
		ScheduledExecutorService execSchedule = Executors.newScheduledThreadPool(10);
		ThreadPoolExecutor execCus = new ThreadPoolExecutor(20, 30, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		return execSchedule;
	}
}
