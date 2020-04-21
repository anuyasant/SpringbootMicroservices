package com.ags.learn.microservices.springbootmicroservicesFE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.ags.learn.microservices.springbootmicroservicesFE.config")
@RibbonClient(name="ex-rate-app")
public class SpringbootMicroservicesFEApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMicroservicesFEApplication.class, args);
	}
}
