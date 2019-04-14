package com.orchestration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orchestration.repository.OrchestrationRepository;
import com.orchestration.repository.ProcessRepository;
import com.orchestration.repository.SourceRepository;
import com.orchestration.service.OrchestrationService;

@RestController
public class OrchestrationController {

	@Autowired
	private OrchestrationService orchestrationService;
	
	@Autowired
	private OrchestrationRepository orchestrationRepo;
	
	@Autowired
	private ProcessRepository processRepo;
	
	@Autowired
	private SourceRepository sourceRepo;
	
	
	@GetMapping("/initialize")
	public String initialize() {
		return orchestrationService.initializeOrchestrator();
	}
	
	@GetMapping("/start")
	public String startOrchestration()
	{
		return orchestrationService.startOrchestration();
	}
	
	@GetMapping("/start/{source}")
	public String startOrchestrationForOne(@PathVariable("source") String source)
	{
		return orchestrationService.startOrchestrationForOneSource(source);
	}
	
	@GetMapping("/getStatus")
	public String getStatus()
	{
		return orchestrationRepo.findAll().get(0).toString();
	}
	
	@RequestMapping(value="/uploadScripts", method=RequestMethod.POST)
	public String uploadScripts(@RequestParam("message") String message) {
		
		StringBuffer finalHtml = new StringBuffer();
		finalHtml.append("<script>");
		finalHtml.append("$(document).ready(function () {");
		finalHtml.append("var table = document.getElementById('result1');");
        
		finalHtml.append("table.innerHTML = '';");

		finalHtml.append("var header = document.createElement('thead');");
		finalHtml.append("var col1 = document.createElement('td');");
		finalHtml.append("col1.innerHTML = 'column1';");
        //col1.style.borderWidth='1px';
		finalHtml.append("var col2 = document.createElement('td');");
		finalHtml.append("col2.innerHTML = 'column2';");
        //col2.style.borderWidth='1px';
		finalHtml.append("var col3 = document.createElement('td');");
		finalHtml.append("col3.innerHTML = 'grouping_method';");
        //col3.style.borderWidth='1px';
		finalHtml.append("var col4 = document.createElement('td');");
		finalHtml.append("col4.innerHTML = 'comments_text';");
        //col4.style.borderWidth='1px';
		finalHtml.append("var col5 = document.createElement('td');");
		finalHtml.append("col5.innerHTML = 'mod_date';");
        //col5.style.borderWidth='1px';
		finalHtml.append("var col6 = document.createElement('td');");
		finalHtml.append("col6.innerHTML = 'updatead_records';");

		finalHtml.append("header.appendChild(col1);");
		finalHtml.append("header.appendChild(col2);");
		finalHtml.append("header.appendChild(col3);");
		finalHtml.append("header.appendChild(col4);");
		finalHtml.append("header.appendChild(col5);");
		finalHtml.append("header.appendChild(col6);");
        //table.appendChild(header);
		finalHtml.append("table.append(header);");

		String[] lines = message.split("\n");
		//finalHtml.append("var rows = $('#message').val().split('\n');");

		for(String line: lines) {
			//finalHtml.append("for (i = 0; i < rows.length; i++) {");

				finalHtml.append("var row = document.createElement('tr');");
				//finalHtml.append("var cols = rows[i].split('\t');");
				//finalHtml.append("for (j = 0; j < cols.length; j++) {");
				String[] columns = line.split("\t");
				//System.out.println("\n");
				for(String col: columns) {
					//System.out.println(col+"\t");
						col = col.replaceAll("\r", "");
						finalHtml.append("var column = document.createElement('td');");
						finalHtml.append("column.innerHTML = '"+col+"';");
						//finalHtml.append("column.append(missingfield);");
						//$(this).css("background-color", "#ffffff");

						finalHtml.append("row.appendChild(column);");
				}
				finalHtml.append("var column = document.createElement('td');");
				finalHtml.append("column.innerHTML = 'num_of_records';");
				finalHtml.append("row.appendChild(column);");
				
				finalHtml.append("table.appendChild(row);");

		}
	
		//console.log(table);
		finalHtml.append("if ($('#message').val().length == 0) {");
		finalHtml.append("$('#result1').remove();");
		finalHtml.append("}");
		finalHtml.append("});");
		
		finalHtml.append("</script>");
		
		//System.out.println(finalHtml.toString());
		return finalHtml.toString();
		
	}
}
