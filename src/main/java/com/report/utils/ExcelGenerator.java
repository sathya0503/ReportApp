  package com.report.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.report.entity.CitizenPlan;
import com.report.repo.CitizenPlanRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ExcelGenerator {


  @Autowired
  private CitizenPlanRepository repo;

  public void generate(HttpServletResponse response, List<CitizenPlan> records, File f) {


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


      FileOutputStream fos = new FileOutputStream(f);
      workbook.write(fos);
      fos.close();

			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			workbook.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
