package com.nt.service;

import java.util.List;
import java.util.Map;

import com.nt.bindings.PlanData;

public interface IPlanMgmtService {
	
	public String registerPlan(PlanData plan);
	public Map<Integer, String> getPlanCategories();//for select catogories
	public List<PlanData> showAllPlans();//for select operation
	public PlanData showPlanById(Integer planId);//for edite operation(to show the existing record for editing
	public String updatePlan(PlanData plan);//for edite operation form submit
	public String deletePlan(Integer planId);// for deletion operation(hard deletion)
	public String changePlanStatus(Integer planId, String status);//for soft deletion
	
	
}
