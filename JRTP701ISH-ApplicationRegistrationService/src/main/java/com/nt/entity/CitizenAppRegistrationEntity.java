package com.nt.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CITIZEN_APPLICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenAppRegistrationEntity {

	@Id
	@SequenceGenerator(name="gen1_seq",sequenceName="aap_id_seq",
													initialValue =100, allocationSize =	1)
	@GeneratedValue(generator ="gen1_seq", strategy= GenerationType.SEQUENCE)
	private Integer appId;
	@Column(length=30)
	private String fullName;
	@Column(length=30)
	private String email;
	private Long phoneNumber;
	private Long ssn;
	@Column(length=10)
	private String gender;
	private LocalDate dob;
	@Column(length=30)
	private String stateName;
	@CreationTimestamp
	@Column(insertable = true, updatable = false)
	private LocalDate createDate;
	@UpdateTimestamp
	@Column(insertable = false, updatable = true)
	private LocalDate updateDate;
	@Column(length=30)
	private String createdBy;
	@Column(length=30)
	private String updatedBy;
	
}
