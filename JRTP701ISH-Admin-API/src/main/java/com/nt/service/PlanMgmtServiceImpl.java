package com.nt.service;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.bindings.PlanData;
import com.nt.config.AppConfigProperties;
import com.nt.entity.PlanCategory;
import com.nt.entity.PlanEntity;
import com.nt.repositary.IPlanCategoryRepositary;
import com.nt.repositary.lPlanRepositary;

@Service
public class PlanMgmtServiceImpl implements IPlanMgmtService {

	@Autowired
	private lPlanRepositary planRepo;
	
	@Autowired
	private IPlanCategoryRepositary planCategoryRepo;
	
	private Map<String, String> message;
	@Autowired
	public PlanMgmtServiceImpl(AppConfigProperties props) {
		message = props.getMessage();
	}
	
	@Override
	public String registerPlan(PlanData plan) {
		PlanEntity planEntity = new PlanEntity();
		BeanUtils.copyProperties(plan, planEntity);
		PlanEntity savedEntity = planRepo.save(planEntity);
		if(savedEntity.getPlanId()!=null) {
			return message.get("save-success") +savedEntity.getPlanId();
		}
		else {
			return message.get("save-failure");
		}
		
	}

	@Override
	public Map<Integer, String> getPlanCategories() {

		//get all plan categories
		List<PlanCategory>list = planCategoryRepo.findAll();
		Map<Integer, String> categoriesMap = new HashMap<Integer, String>();
		list.forEach(category->{
			categoriesMap.put(category.getCategoryId(), category.getCategoryName());
		});
		return categoriesMap;
	}

	@Override
	public List<PlanData> showAllPlans() {

		List<PlanEntity>planEntitylist =  planRepo.findAll();
		List<PlanData> planDatalist = new ArrayList();
		planEntitylist.forEach(planEntity->{
			PlanData data = new PlanData();
			BeanUtils.copyProperties(planEntity, data);
			planDatalist.add(data);
		});
		return planDatalist;
	}

	@Override
	public PlanData showPlanById(Integer planId) {
		
		//return planRepo.findById(planId).orElseThrow(()->new IllegalArgumentException(message.get("find-by-id-failure")));
		
		Optional<PlanEntity>opt = planRepo.findById(planId);	
		PlanData planData = new PlanData();
		if(opt.isPresent()) {
			PlanEntity planEntity =  opt.get();
			BeanUtils.copyProperties(planEntity, planData);
			return planData;		
		}
		else {
			throw new IllegalArgumentException("Plan id Not found");
		}
	}

	@Override
	public String updatePlan(PlanData plan) {

		if(plan.getPlanName()!=null) {
			PlanEntity planEntity = new PlanEntity();
			BeanUtils.copyProperties(plan, planEntity);
			planRepo.save(planEntity);
			return plan.getPlanName()+message.get("update-success");
		}
		else {
			return plan.getPlanName()+message.get("update-failure");
		}
		
	}

	@Override
	public String deletePlan(Integer planId) {

		Optional<PlanEntity> opt =planRepo.findById(planId);
		if(opt.isPresent()) {
			planRepo.deleteById(planId);
			return planId +message.get("delete-success");
		}
		else {
			return planId +message.get("delete-failure");
		}
	}

	@Override
	public String changePlanStatus(Integer planId, String status) {

		Optional<PlanEntity>opt = planRepo.findById(planId);
		if(opt.isPresent()) {
			PlanEntity plan = opt.get();
			plan.setActiveSw(status);
			planRepo.save(plan);
			return planId +message.get("status-change-success");
		}
		else {
			return planId+message.get("status-change-failure");
		}
	}

	

}
