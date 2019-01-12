package com.orchestration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orchestration.repository.OrchestrationRepository;
import com.orchestration.repository.ProcessRepository;
import com.orchestration.repository.SourceRepository;
import com.orchestration.service.OrchestrationService;

@RestController
public class OrchestrationController {

	@Autowired
	private OrchestrationService orchestrationService;
	
	@Autowired
	private OrchestrationRepository orchestrationRepo;
	
	@Autowired
	private ProcessRepository processRepo;
	
	@Autowired
	private SourceRepository sourceRepo;
	
	
	@GetMapping("/initialize")
	public String initialize() {
		return orchestrationService.initializeOrchestrator();
	}
	
	@GetMapping("/getStatus")
	public String getStatus()
	{
		return orchestrationRepo.findAll().get(0).toString();
	}
	
}
