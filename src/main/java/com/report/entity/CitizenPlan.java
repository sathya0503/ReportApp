package com.report.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="CITIZEN_PLANS_INFO")
public class CitizenPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer citizenId;
	
	private String citizenName;
	
	private String gender;
	
	private String planName;
	
	private String planStatus;
	
	private LocalDate planStartDate;
	
	private LocalDate planEndDate;
	
	private Double benefitsAmt;
	
	private String denialReason;
	
	private LocalDate terminationDate;
	
	private String terminationRsn;
	

}
