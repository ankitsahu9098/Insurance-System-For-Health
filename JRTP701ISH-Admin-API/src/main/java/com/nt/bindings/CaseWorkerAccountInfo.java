package com.nt.bindings;

import java.time.LocalDate;

import lombok.Data;
@Data
public class CaseWorkerAccountInfo {
	
	private String fullName;
	private String email;
	private String pwd;
	private String gender;
	private Long SSN;
	private LocalDate DOB;

}
