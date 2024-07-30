package com.example.user_registration.service;

import com.example.user_registration.model.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PDFGenerationService {

    public ByteArrayInputStream generateUsersPDF(List<User> users) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Registered Users", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Create table
            PdfPTable table = new PdfPTable(5); // 5 columns for ID, First Name, Last Name, Email, DOB
            table.setWidthPercentage(100);

            // Add table headers
            String[] headers = {"ID", "First Name", "Last Name", "Email", "Date of Birth"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            // Add user data
            for (User user : users) {
                table.addCell(String.valueOf(user.getId()));
                table.addCell(user.getFirstName());
                table.addCell(user.getLastName());
                table.addCell(user.getEmail());
                table.addCell(user.getDob());
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}