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
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {

	private CitizenPlanRepository repo;

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

		List<CitizenPlan> records = repo.findAll();

		try (Workbook workbook = new XSSFWorkbook()) {

			Sheet sheet = workbook.createSheet("plans-data");

			Row rowHeader = sheet.createRow(0);

			rowHeader.createCell(0).setCellValue("ID");
			rowHeader.createCell(1).setCellValue("Name");
			rowHeader.createCell(2).setCellValue("Gender");
			rowHeader.createCell(3).setCellValue("Plan Name");
			rowHeader.createCell(4).setCellValue("Plan Status");
			rowHeader.createCell(5).setCellValue("Plan Start Date");
			rowHeader.createCell(6).setCellValue("Plan End Date");
			rowHeader.createCell(7).setCellValue("Benefit Amount");
			rowHeader.createCell(8).setCellValue("Denial Reason");
			rowHeader.createCell(9).setCellValue("Termination Date");
			rowHeader.createCell(10).setCellValue("Termination Reason");

			int dataRowIndex = 1;
			for (CitizenPlan plan : records) {
				Row dataRow = sheet.createRow(dataRowIndex);

				dataRow.createCell(0).setCellValue(plan.getCitizenId());
				dataRow.createCell(1).setCellValue(plan.getCitizenName());
				dataRow.createCell(2).setCellValue(plan.getGender());
				dataRow.createCell(3).setCellValue(plan.getPlanName());
				dataRow.createCell(4).setCellValue(plan.getPlanStatus());

				if (null != plan.getPlanStartDate()) {
					dataRow.createCell(5).setCellValue(plan.getPlanStartDate() + "");
				} else {
					dataRow.createCell(5).setCellValue("NA");
				}

				if (null != plan.getPlanEndDate()) {
					dataRow.createCell(6).setCellValue(plan.getPlanEndDate() + "");
				} else {
					dataRow.createCell(6).setCellValue("NA");
				}

				if (null != plan.getBenefitsAmt()) {
					dataRow.createCell(7).setCellValue(plan.getBenefitsAmt());
				} else {
					dataRow.createCell(7).setCellValue("NA");
				}
				dataRow.createCell(8).setCellValue(plan.getDenialReason());
				dataRow.createCell(9).setCellValue(plan.getTerminationDate());
				dataRow.createCell(10).setCellValue(plan.getTerminationRsn());
				dataRowIndex++;
			}

			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			workbook.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) {

		try {

			Document document = new Document(PageSize.A4);
			PdfWriter.getInstance(document, response.getOutputStream());

			document.open();

			Font fontTile = FontFactory.getFont(FontFactory.TIMES_ROMAN);
			fontTile.setSize(20);
			Paragraph p = new Paragraph("Citizen Plan Info", fontTile);
			p.setAlignment(Paragraph.ALIGN_CENTER);

			document.add(p);

			PdfPTable table = new PdfPTable(11);

			table.setWidthPercentage(100f);
			// table.setWidths(new int[] {3, 3, 3});
			table.setSpacingBefore(5);

			table.addCell("Citizen ID");
			table.addCell("Citizen Name");
			table.addCell("Gender");
			table.addCell("Plan Name");
			table.addCell("Plan Status");
			table.addCell("Plan Start Date");
			table.addCell("Pland End Date");
			table.addCell("Benefits Amount");
			table.addCell("Denial Reason");
			table.addCell("Termination Date");
			table.addCell("Termination Reason");

			List<CitizenPlan> records = repo.findAll();

			for (CitizenPlan plan : records) {
				table.addCell(String.valueOf(plan.getCitizenId()));
				table.addCell(plan.getCitizenName());
				table.addCell(plan.getGender());
				table.addCell(plan.getPlanName());
				table.addCell(plan.getPlanStatus());
				table.addCell(plan.getPlanStartDate()+"");
				table.addCell(plan.getPlanEndDate()+"");
				table.addCell(String.valueOf(plan.getBenefitsAmt()));
				table.addCell(plan.getDenialReason());
				table.addCell(plan.getTerminationDate()+"");
				table.addCell(plan.getTerminationRsn());
			}

			document.add(table);
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
