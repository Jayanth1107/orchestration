package com.orchestration.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Source {

	@Id
	private int sourceId;
	private String sourceName;
	private int count;

	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
