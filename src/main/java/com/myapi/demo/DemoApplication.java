package com.myapi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class DemoApplication { // main 함수를 run 하면  스프링부트 실행

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
