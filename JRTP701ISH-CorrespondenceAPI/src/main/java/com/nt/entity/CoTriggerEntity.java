package com.nt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ISH_CO_TRIGGERS")
@Data
public class CoTriggerEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer coTriggerId;
	private Integer caseNumber;
	@Lob
	@Column(length=10000)
	private byte[] coNoticePdf;
	private String triggerStatus="pendding";
	

}
