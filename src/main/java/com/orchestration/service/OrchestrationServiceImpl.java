package com.orchestration.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
				orch.setSource(s.getSourceId()+"");
				orch.setProcess(p.getProcessId()+"");
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
		
		int count=0;
		
		List<Source> sourceList = sourceRepo.findAll();
		List<Process> processList = processRepo.findAll();
		
		ExecutorService es = Executors.newFixedThreadPool(sourceList.size() * processList.size());
		
		for(Source s : sourceList) {
			for(Process p : processList) {				
				
				Runnable r = new Runnable() {
			
					@Override
					public void run() {
						startAllProcesses(s.getSourceName(), p.getProcessName());
					}
				};
				es.execute(r);
			}
		}
		while(!es.isTerminated()) {
			
		}
		logger.info("End of all the processes");
		return "End of orchestration";
	}

	protected void startAllProcesses(String source, String process) {		

		String key = source+"-"+process;
		String status="";
		
		Orchestration current = orchestrationRepo.findOneBySourceProcessKey(key);
		
		if(current == null || current.getStatus() == "DONE") return;
		
		try {
		if(current.getPreviousProcess()==null) {
			logger.info("No Previous process found, initiating sequence with : "+ key);
		}
		else {
			current.setStatus("WAITING");
			orchestrationRepo.save(current);
		}
		}
		catch(Exception e) {}
		
		if(current.getPreviousProcess()!=null)
		{
			status = orchestrationRepo.findOneBySourceProcessKey(source+"-"+current.getPreviousProcess()).getStatus();
			while(status!="DONE") {
				try {Thread.sleep(10000);}catch(Exception e) {}
				status = orchestrationRepo.findOneBySourceProcessKey(source+"-"+current.getPreviousProcess()).getStatus();
			}
		}
		
		logger.info("Starting process : "+key);
		try {
			current.setStatus("RUNNING");
			orchestrationRepo.save(current);
			Thread.sleep(10000);
		}
		catch(Exception e) {
			
		}
		
		current.setStatus("DONE");
		orchestrationRepo.save(current);
		
		logger.info("End of process : "+key);
	}

	@Override
	public String startOrchestrationForOneSource(String source) {
		// TODO Auto-generated method stub
		return null;
	}

}
