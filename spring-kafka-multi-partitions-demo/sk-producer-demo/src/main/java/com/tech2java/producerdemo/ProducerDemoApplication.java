package com.tech2java.producerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProducerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerDemoApplication.class, args);
	}

}
