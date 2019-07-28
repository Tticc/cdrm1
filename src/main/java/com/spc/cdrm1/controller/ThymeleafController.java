package com.spc.cdrm1.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thy")
public class ThymeleafController {

	
	@GetMapping("/{path}")
	public String getPath(HttpServletRequest request,@PathVariable String path) {
		return path;
	}
}
