package com.report.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.report.entity.CitizenPlan;
import com.report.repo.CitizenPlanRepository;
import com.report.request.SearchRequest;
import com.report.utils.EmailUtils;
import com.report.utils.ExcelGenerator;
import com.report.utils.PdfGenerator;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository repo;

	@Autowired
	private ExcelGenerator excelGenerator;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private EmailUtils emailUtils;

	public ReportServiceImpl(CitizenPlanRepository repo) {
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

		if (null != request.getPlanName() && "" != request.getPlanName()) {
			entity.setPlanName(request.getPlanName());
		}

		if (null != request.getPlanStatus() && "" != request.getPlanStatus()) {
			entity.setPlanStatus(request.getPlanStatus());
		}

		if (null != request.getGender() && "" != request.getGender()) {
			entity.setGender(request.getGender());
		}

		if (null != request.getPlanStartDate() && "" != request.getPlanStartDate()) {
			String startDate = request.getPlanStartDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(startDate, formatter);
			entity.setPlanStartDate(localDate);
		}

		if (null != request.getPlanEndDate() && "" != request.getPlanEndDate()) {
			String endDate = request.getPlanEndDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(endDate, formatter);
			entity.setPlanEndDate(localDate);
		}

		return repo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) {

		File f = new File("plans.xlsx");


		List<CitizenPlan> records = repo.findAll();

		excelGenerator.generate(response, records, f);

		//Mail sender
		String subject = "Report of Citizens plans";
		String body = "<h1>Testing mail</h1>";
		String to = "sathyayadav0503@gmail.com";

		emailUtils.sendMail(subject, body, to, f);

		f.delete();
		return false;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) {

		File f = new File("plans.pdf");

		List<CitizenPlan> records = repo.findAll();

		pdfGenerator.generate(response, records, f);

		//Mail sender
		String subject = "Report of Citizens plans";
		String body = "<h1>Testing mail</h1>";
		String to = "sathyayadav0503@gmail.com";

		emailUtils.sendMail(subject, body, to, f);

		f.delete();
		return true;
	}

}
