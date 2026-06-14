package com.springshift.pro.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.springshift.pro.dto.MigrationTask;
import org.springframework.stereotype.Service;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportGenerator {
    public byte[] generateRoadmapPdf(List<MigrationTask> tasks, double score) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.DARK_GRAY);
        Paragraph title = new Paragraph("Spring-Shift Pro: Migration Blueprint", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Modernization Readiness Score: " + String.format("%.2f", score) + "%"));
        document.add(new Paragraph(" "));
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        addTableHeader(table);
        for (MigrationTask task : tasks) {
            table.addCell(task.getCategory());
            table.addCell(task.getImpact());
            table.addCell(task.getDescription() + "\nSuggestion: " + task.getSuggestion());
        }
        document.add(table);
        document.close();
        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Font f = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
        String[] headers = {"Category", "Impact", "Details & Mitigation"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, f));
            cell.setBackgroundColor(Color.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }
}
