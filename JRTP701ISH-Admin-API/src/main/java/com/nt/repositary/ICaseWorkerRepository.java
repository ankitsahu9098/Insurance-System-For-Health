package com.nt.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.CaseWorkerAccounts;

public interface ICaseWorkerRepository extends JpaRepository<CaseWorkerAccounts, Integer> {

}
