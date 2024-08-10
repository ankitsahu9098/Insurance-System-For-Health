package com.nt.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssa-web-api")
public class SSAWebOperationsRestController {
	
	@GetMapping("/find/{ssn}")
	public ResponseEntity<String> getStateBySSN(@PathVariable Integer ssn){
		
		if(String.valueOf(ssn).length()!=9)
			return new ResponseEntity<String>("invalid ssn",HttpStatus.BAD_REQUEST);
		
		//get state name
		int stateCode = ssn%100;
		String stateName = null;
		
		if(stateCode==01)
			stateName="Alabama";
		else if(stateCode==02)
			stateName="Alaska";
		else if(stateCode==04)
			stateName="Arizona";
		else if(stateCode==05)
			stateName="Arkansas";
		else if(stateCode==06)
			stateName="California";
		else if(stateCode==8)
			stateName="Colorado";
		else if(stateCode==9)
			stateName="Connecticut";
		else if(stateCode==10)
			stateName="Delaware";
		else if(stateCode==11)
			stateName="Columbia";
		else if(stateCode==12)
			stateName="Florida";
		else if(stateCode==13)
			stateName="Georgia";
		else if(stateCode==15)
			stateName="Hawaii";
		else if(stateCode==16)
			stateName="Idaho";
		else if(stateCode==17)
			stateName="Illinois";
		else if(stateCode==18)
			stateName="Indiana";
		else if(stateCode==19)
			stateName="Iowa";
		else if(stateCode==20)
			stateName="Kansas";
		else if(stateCode==21)
			stateName="Kentucky";
		else if(stateCode==22)
			stateName="Louisiana";
		else if(stateCode==23)
			stateName="Maine";
		else
		     stateName="Invalid SSN";
		
		return new ResponseEntity<String>(stateName, HttpStatus.OK);
	}

}
