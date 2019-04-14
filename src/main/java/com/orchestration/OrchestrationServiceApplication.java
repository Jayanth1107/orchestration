package com.orchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication//(scanBasePackages= {"com.orchestration.service", "com.orchestration.controller", "com.orchestration.entity", "com.orchestration.repository"})
@EnableScheduling
public class OrchestrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestrationServiceApplication.class, args);
	}

	/*@Scheduled(fixedRate=5000)
	public void test(){
		HomeController.variedText=""+Math.random();
	}*/
}

