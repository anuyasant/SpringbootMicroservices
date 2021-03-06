package com.ags.learn.microservices.springbooteurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringbooteurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbooteurekaserverApplication.class, args);
	}

}
