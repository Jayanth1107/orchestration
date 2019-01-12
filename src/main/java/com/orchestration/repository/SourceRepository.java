package com.orchestration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orchestration.entity.Source;

public interface SourceRepository extends JpaRepository<Source, Integer> {
	
	public Source findAllByOrderBySourceIdAsc();

}
