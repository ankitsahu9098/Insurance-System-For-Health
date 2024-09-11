package com.nt.bindings;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PlanData {
	
	private String planName;
	private String discription;
	private LocalDate startDate;
	private LocalDate endDate;
	private String activeSw;

}
