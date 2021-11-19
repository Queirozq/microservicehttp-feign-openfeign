package com.Queirozq.onlinestore.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OnlineStoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreServiceApplication.class, args);
	}

}
