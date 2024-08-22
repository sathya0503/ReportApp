package com.report.runner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.report.entity.CitizenPlan;
import com.report.repo.CitizenPlanRepository;

@Component
public class DataLoader implements ApplicationRunner{

	@Autowired
	private CitizenPlanRepository repo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		repo.deleteAll();
		
		CitizenPlan c1 = new CitizenPlan();
		c1.setCitizenName("Maya");
		c1.setGender("Female");
		c1.setPlanName("Cash");
		c1.setPlanStatus("Approved");
		c1.setPlanStartDate(LocalDate.now());
		c1.setPlanEndDate(LocalDate.now().plusMonths(6));
		
		CitizenPlan c2 = new CitizenPlan();
		c2.setCitizenName("Miler");
		c2.setGender("Male");
		c2.setPlanName("Online");
		c2.setPlanStatus("Denied");
		c2.setDenialReason("Property not satisfies");

		CitizenPlan c3 = new CitizenPlan();
		c3.setCitizenName("Alina");
		c3.setGender("Female");
		c3.setPlanName("Online");
		c3.setPlanStatus("Denied");
		c3.setDenialReason("Not proper data");


		CitizenPlan c4 = new CitizenPlan();
		c4.setCitizenName("Jin");
		c4.setGender("Male");
		c4.setPlanName("Cash");
		c4.setPlanStatus("Approved");
		c4.setPlanStartDate(LocalDate.now());
		c4.setPlanEndDate(LocalDate.now().plusMonths(8));
		
		CitizenPlan c5 = new CitizenPlan();
		c5.setCitizenName("Alberto");
		c5.setGender("Male");
		c5.setPlanName("Cash");
		c5.setPlanStatus("Approved");
		c5.setPlanStartDate(LocalDate.now());
		c5.setPlanEndDate(LocalDate.now().plusMonths(10));

		CitizenPlan c6 = new CitizenPlan();
		c6.setCitizenName("Alberto");
		c6.setGender("Male");
		c6.setPlanName("Cash");
		c6.setPlanStatus("Approved");
		c6.setTerminationDate(LocalDate.now());
		c6.setTerminationRsn("Plan Ends");
		
		List<CitizenPlan> list = Arrays.asList(c1, c2, c3, c4, c5, c6);

		repo.saveAll(list);
		
		
	}

}
