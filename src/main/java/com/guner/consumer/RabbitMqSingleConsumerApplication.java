package com.guner.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMqSingleConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMqSingleConsumerApplication.class, args);
	}

}
