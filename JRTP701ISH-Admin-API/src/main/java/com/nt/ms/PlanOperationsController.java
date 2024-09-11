package com.nt.ms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.bindings.PlanData;
import com.nt.service.IPlanMgmtService;

@RestController
@RequestMapping("/plan-api")
public class PlanOperationsController {
	@Autowired
	IPlanMgmtService planService;
	
	@GetMapping("/categories")
	public ResponseEntity<?> showPlanCategories(){
			Map<Integer,String>mapCategories = planService.getPlanCategories();
			return new ResponseEntity<Map<Integer,String>>(mapCategories, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> savePlan(@RequestBody PlanData plan){
			String msg = planService.registerPlan(plan);
			return new ResponseEntity<String>(msg,HttpStatus.OK);
	
	}
	@GetMapping("/all")
	public ResponseEntity<?> getAllPlan(){
			List<PlanData>list = planService.showAllPlans();
			return new ResponseEntity<List<PlanData>>(list, HttpStatus.OK);
	}
	@GetMapping("/find/{planId}")
	public ResponseEntity<?> getPlanById(@PathVariable Integer planId){
			PlanData plan = planService.showPlanById(planId);
			return new ResponseEntity<PlanData>(plan, HttpStatus.OK);
	}
	@PutMapping("/update")
	public ResponseEntity<?> updatePlan(@RequestBody PlanData plan){
		String msg  = planService.updatePlan(plan);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
}
	@DeleteMapping("/delete/{planId}")
	public ResponseEntity<?> removePlanByPlanId(@PathVariable Integer planId){
	
			String msg = planService.deletePlan(planId);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		
	}
	
	@PutMapping("/status-change/{planId}/{staus}")
	public ResponseEntity<?> removePlanByPlanId(@PathVariable Integer planId, @PathVariable String staus){
			String msg = planService.changePlanStatus(planId, staus);
			return new ResponseEntity<String>(msg, HttpStatus.OK);
			
	}
	
}

