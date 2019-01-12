package com.orchestration.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Process {

	@Id
	private int processId;
	private String processName;
	private String endpoint;
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
}
