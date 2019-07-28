package com.spc.cdrm1.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ErrorInterceptor implements ErrorPageRegistrar {
	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, "/login/404_notfound");//跳转到index
		//ErrorPage e500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/vue/get/2");//跳转到index
		registry.addErrorPages(e404/*,e500*/);
	}
}
