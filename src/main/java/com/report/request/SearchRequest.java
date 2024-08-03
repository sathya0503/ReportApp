package com.report.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SearchRequest {
	
	private String planName;
	
	private String planStatus;
	
	private String gender;
	
	private LocalDate planStartDate;
	
	private LocalDate planEndDate;
	

}
