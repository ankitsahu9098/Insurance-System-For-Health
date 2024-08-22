package com.nt.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nt.binding.CitizenAppRegistrationInputs;
import com.nt.entity.CitizenAppRegistrationEntity;
import com.nt.repository.IApplicationRegistrationRepositary;
@Service
public class CitizenApplicationRegistrationServiceImpl implements ICitizenApplicationRegistrationService {

	@Autowired
	private IApplicationRegistrationRepositary citizenRepo;
	@Autowired
	private RestTemplate template;
	@Value("${ar.ssa-web.url}")
	private String endpointUrl;
	@Value("${ar.state}")
	private String targetState;
	
	
	@Override
	public Integer registerCitizenApplication(CitizenAppRegistrationInputs inputs) {
		//perform web-service call to chack weather SSN is valid or not and to get the state name
		ResponseEntity<String> response = template.exchange(endpointUrl, HttpMethod.GET, null, String.class,inputs.getSsn());
		String stateName = response.getBody();
		
		//register citizen if he belongs to California state (CA)
		if(stateName.equalsIgnoreCase(targetState)) {
			//prepare the Entity Object
			CitizenAppRegistrationEntity entity = new CitizenAppRegistrationEntity();
			BeanUtils.copyProperties(inputs, entity);
			entity.setStateName(stateName);
			//save the object
			int aapId = citizenRepo.save(entity).getAppId();
			return aapId;
		}
		return 0;
	}

}
