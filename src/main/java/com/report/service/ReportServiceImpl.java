package com.report.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.report.entity.CitizenPlan;
import com.report.repo.CitizenPlanRepository;
import com.report.request.SearchRequest;

@Service
public class ReportServiceImpl implements ReportService {

	private CitizenPlanRepository repo;

	public ReportServiceImpl(CitizenPlanRepository repo){
		this.repo = repo;
	}


	@Override
	public List<String> getPlanNames() {

		return repo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatuses() {
		
		return repo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {

		CitizenPlan entity = new CitizenPlan();

		if(null != request.getPlanName() && ""!= request.getPlanName()){
			entity.setPlanName(request.getPlanName());
		}

		if(null != request.getPlanStatus() && ""!= request.getPlanStatus()){
			entity.setPlanStatus(request.getPlanStatus());
		}

		if(null != request.getGender() && ""!= request.getGender()){
			entity.setGender(request.getGender());
		}

		if(null != request.getPlanStartDate() && ""!= request.getPlanStartDate()){
			String startDate = request.getPlanStartDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(startDate, formatter);
			entity.setPlanStartDate(localDate);
		}

		if(null != request.getPlanEndDate() && ""!= request.getPlanEndDate()){
			String endDate = request.getPlanEndDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(endDate, formatter);
			entity.setPlanEndDate(localDate);
		}

		
		return repo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exportPdf() {
		// TODO Auto-generated method stub
		return false;
	}

}
