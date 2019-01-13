package com.orchestration.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchestration.entity.Orchestration;
import com.orchestration.entity.Process;
import com.orchestration.entity.Source;
import com.orchestration.repository.OrchestrationRepository;
import com.orchestration.repository.ProcessRepository;
import com.orchestration.repository.SourceRepository;

enum ProcessSequence{
	step1("INTAKE"),
	step2("SQL_INJECTOR"),
	step3("ADDRESS_CLEANSE"),
	step4("DNB_PROCESS"),
	step5("ADDRESS_PARSE"),
	step6("ACCOUNT_ROUNDING");

	private final String stepName;       

    private ProcessSequence(String s) {
    	stepName = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return stepName.equals(otherName);
    }

    public String toString() {
       return this.stepName;
    }
}

@Service
public class OrchestrationServiceImpl implements OrchestrationService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(OrchestrationServiceImpl.class);
	
	@Autowired
	private OrchestrationRepository orchestrationRepo;
	
	@Autowired
	private ProcessRepository processRepo;
	
	@Autowired
	private SourceRepository sourceRepo;
	
	@Override
	public String initializeOrchestrator() {
		
		logger.info("Initializing Orchestration");
		
		boolean next = false;
		String prevProcess = null;
		ProcessSequence[] sequence= ProcessSequence.values();

		if(orchestrationRepo.findAll().size()>0) {
			return "Already Initialized";
		}
		
		for(Source s : sourceRepo.findAll())
		{
			for(Process p: processRepo.findAll())
			{
				Orchestration orch = new Orchestration();
				
				orch.setSourceProcessKey(s.getSourceName()+"-"+p.getProcessName());
				orch.setSource(s.getSourceName());
				orch.setProcess(p.getProcessName());
				orch.setCount(s.getCount());
				orch.setStatus("READY");
				
				for(ProcessSequence ps : sequence)
				{
					if(next==true) {
						orch.setNextProcess(ps.toString());
						next = false;
						break;
					}
					if(ps.equalsName(p.getProcessName())) {
						orch.setPreviousProcess(prevProcess);
						next = true;
					}
					prevProcess = ps.toString();
				}
				
				orchestrationRepo.save(orch);
			}
			next = false;
			prevProcess = null;
		}
		
		return "Initialization Done";
	}

	@Override
	public String startOrchestration() {
		return null;
	}

	@Override
	public String startOrchestrationForOneSource(String source) {
		// TODO Auto-generated method stub
		return null;
	}

}
