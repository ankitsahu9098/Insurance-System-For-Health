package com.nt.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ISH_PLAN_MASTER")
@Data
public class PlanEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer planId;
	@Column(length=30)
	private String planName;
	@Column(length=100)
	private String discription;
	private LocalDate startDate;
	private LocalDate endDate;
	@Column(length=30)
	private String activeSw;
	@CreationTimestamp
	@Column(updatable = false, insertable = true)
	private LocalDate creationDate;
	@UpdateTimestamp
	@Column(updatable = true,insertable = false)
	private LocalDate updationDate;
	@Column(length=30)
	private String createdBy;
	@Column(length=30)
	private String updatedBy;
	
	

}
