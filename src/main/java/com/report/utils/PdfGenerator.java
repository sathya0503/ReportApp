package com.report.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.report.entity.CitizenPlan;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class PdfGenerator {



    public void generate(HttpServletResponse response, List<CitizenPlan> records, File f) {

		try {

			Document document = new Document(PageSize.A4);
			PdfWriter.getInstance(document, response.getOutputStream());
            PdfWriter.getInstance(document, new FileOutputStream(f));

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

		
	}

}


