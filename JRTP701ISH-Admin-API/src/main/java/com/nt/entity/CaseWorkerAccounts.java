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
@Table(name="CASE_WORKER_ACCOUNTS")
@Data
public class CaseWorkerAccounts {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer accountId;
		@Column(length=30)
		private String fullName;
		@Column(length=30)
		private String email;
		@Column(length=30)
		private String pwd;
		@Column(length=30)
		private String gender;
		private Long SSN;
		private LocalDate DOB;
		@CreationTimestamp
		@Column(updatable = false)
		private LocalDate createDate;
		@UpdateTimestamp
		@Column(insertable = false)
		private LocalDate updateDate;
		@Column(length=30)
		private String createdBy;
		@Column(length=30)
		private String updatedBy;
		
}
