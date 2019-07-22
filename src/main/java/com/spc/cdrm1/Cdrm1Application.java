package com.spc.cdrm1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Cdrm1Application {

	public static void main(String[] args) {
		SpringApplication.run(Cdrm1Application.class, args);
	}

}
