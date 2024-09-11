package com.nt.service;

import java.util.List; 

import com.nt.bindings.CaseWorkerAccountInfo;

public interface ICaseWorkerMgmtService {
	
	public String registerCaseWorker(CaseWorkerAccountInfo info);
	public CaseWorkerAccountInfo findCaseWorkerById(Integer accountId);
	public List<CaseWorkerAccountInfo> findAllCaseWorker();
	public String updateCaseWorker(CaseWorkerAccountInfo info);
	public String deleteCaseWorkerById(Integer accountId);

}
