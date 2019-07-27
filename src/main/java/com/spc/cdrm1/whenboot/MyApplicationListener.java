package com.spc.cdrm1.whenboot;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent>{
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("*****************************************************************");
		log.info("now, the application is starting");
		log.info("*****************************************************************");
	}
}
