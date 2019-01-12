package com.orchestration.service;

public interface OrchestrationService {
	
	public String initializeOrchestrator();
	public String startOrchestratio();
	public String startOrchestrationForOneSource(String source);

}
