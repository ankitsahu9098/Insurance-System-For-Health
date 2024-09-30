package com.nt.ms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.binding.CoSummary;
import com.nt.service.ICorrespondenceMgmtService;

@RestController
@RequestMapping("co-trigger-api")
public class CoTriggerOperationsController {
	
	@Autowired
	private ICorrespondenceMgmtService coService;
	@GetMapping("/process")
	public ResponseEntity<CoSummary> processAndUpdateTrigger() throws Exception{
		CoSummary summary = coService.processPendingTriggers();
		return new ResponseEntity<CoSummary>(summary, HttpStatus.OK);
	}

}
