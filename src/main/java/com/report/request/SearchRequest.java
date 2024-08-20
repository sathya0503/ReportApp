package com.report.request;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class SearchRequest {
	
	private String planName;
	
	private String planStatus;
	
	private String gender;
	
	private String planStartDate;
	
	private String planEndDate;
	

}
