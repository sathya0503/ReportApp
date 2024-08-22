package com.report.service;

import java.util.List;

import com.report.entity.CitizenPlan;
import com.report.request.SearchRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {
	
	public List<String> getPlanNames();
	
	public List<String> getPlanStatuses();
	
	public List<CitizenPlan> search(SearchRequest request);
	
	public boolean exportExcel(HttpServletResponse response);
	
	public boolean exportPdf(HttpServletResponse response);
	
}
