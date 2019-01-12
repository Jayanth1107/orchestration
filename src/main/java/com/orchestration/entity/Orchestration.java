package com.orchestration.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Orchestration {
	
	@Id
	private String sourceProcessKey;
	private String source;
	private String process;
	private long count;
	private String status;
	private String previousProcess;
	private String nextProcess;
	private String errorLog;

	public String getSourceProcessKey() {
		return sourceProcessKey;
	}
	public void setSourceProcessKey(String sourceProcessKey) {
		this.sourceProcessKey = sourceProcessKey;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPreviousProcess() {
		return previousProcess;
	}
	public void setPreviousProcess(String previousProcess) {
		this.previousProcess = previousProcess;
	}
	public String getNextProcess() {
		return nextProcess;
	}
	public void setNextProcess(String nextProcess) {
		this.nextProcess = nextProcess;
	}
	public String getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}

}
