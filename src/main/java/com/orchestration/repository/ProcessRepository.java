package com.orchestration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.orchestration.entity.Process;

public interface ProcessRepository extends JpaRepository<Process, Integer> {

	public Process findAllByOrderByProcessIdAsc();
}
