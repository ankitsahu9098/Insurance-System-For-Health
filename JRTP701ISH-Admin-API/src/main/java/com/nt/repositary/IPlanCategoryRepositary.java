package com.nt.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.PlanCategory;

public interface IPlanCategoryRepositary extends JpaRepository<PlanCategory, Integer> {


}
