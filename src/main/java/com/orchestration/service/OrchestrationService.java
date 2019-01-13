package com.orchestration.service;

public interface OrchestrationService {
	
	public String initializeOrchestrator();
	public String startOrchestration();
	public String startOrchestrationForOneSource(String source);

}
