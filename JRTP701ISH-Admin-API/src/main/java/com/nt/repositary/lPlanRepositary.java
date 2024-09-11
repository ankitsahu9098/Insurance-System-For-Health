package com.nt.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.bindings.PlanData;
import com.nt.entity.PlanEntity;

public interface lPlanRepositary extends JpaRepository<PlanEntity, Integer	> {

	public PlanData findByPlanName(String planName);

}
