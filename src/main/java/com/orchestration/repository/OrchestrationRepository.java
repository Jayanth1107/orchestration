package com.orchestration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orchestration.entity.Orchestration;

public interface OrchestrationRepository extends JpaRepository<Orchestration, Integer> {
	
	public Orchestration findAllByOrderBySourceProcessKeyAsc();
	public Orchestration findOneBySourceProcessKey(String key);

}
