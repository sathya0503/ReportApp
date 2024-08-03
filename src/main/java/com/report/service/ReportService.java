package com.report.service;

import java.util.List;

import com.report.entity.CitizenPlan;
import com.report.request.SearchRequest;

public interface ReportService {
	
	public List<CitizenPlan> getPlanNames();
	
	public List<CitizenPlan> getPlanStatuses();
	
	public List<CitizenPlan> search(SearchRequest request);
	
	public boolean exportExcel();
	
	public boolean exportPdf();
	
}
