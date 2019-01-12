package com.orchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(scanBasePackages= {"com.orchestration.service", "com.orchestration.controller", "com.orchestration.entity", "com.orchestration.repository"})
public class OrchestrationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestrationServiceApplication.class, args);
	}

}

