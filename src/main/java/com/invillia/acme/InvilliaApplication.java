package com.invillia.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.invillia.acme")
@EnableAutoConfiguration
public class InvilliaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvilliaApplication.class, args);
	}
}
