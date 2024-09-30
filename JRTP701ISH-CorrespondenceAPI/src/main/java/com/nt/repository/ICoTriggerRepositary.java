package com.nt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.CoTriggerEntity;

public interface ICoTriggerRepositary extends JpaRepository<CoTriggerEntity, Integer> {

	public List<CoTriggerEntity> findByTriggerStatus(String status);
	public CoTriggerEntity findByCaseNumber(Integer caseNumber);

}
