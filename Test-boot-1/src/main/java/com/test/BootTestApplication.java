package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootTestApplication {

	public static void main(String[] args) {
System.out.print("hello");
		SpringApplication.run(BootTestApplication.class, args);
	}

}
