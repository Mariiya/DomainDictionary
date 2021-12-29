package com.domaindictionary.service;

import com.domaindictionary.model.DictionaryEntry;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class DomainDictionaryService {
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    public ByteArrayInputStream createDomainDictionary(List<DictionaryEntry> entries) {
        Document document = new Document();
        try {
            String FONT_MAIN = "./src/main/resources/fonts/arial.ttf";
            String FONT_TITLE = "./src/main/resources/fonts/BOD_B.TTF";
            BaseFont bf = BaseFont.createFont(FONT_MAIN, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont bfTitle = BaseFont.createFont(FONT_TITLE, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font fontTerm = new Font(bf, 14, Font.BOLD);
            Font fontDefinition = new Font(bf, 14, Font.NORMAL);
            Font fontTitle = new Font(bfTitle, 16, Font.NORMAL);

            PdfWriter.getInstance(document, out);
            document.open();
            Paragraph p = new Paragraph();
            p.setFont(fontTitle);
            p.add("Domain Dictionary \n" + '\n');
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            Paragraph p2 = new Paragraph();
            for (DictionaryEntry entry : entries) {
                p2.setFont(fontTerm);
                p2.add(entry.getTerm());
                p2.setFont(fontDefinition);
                p2.add(" - " + entry.getDefinition() + '\n');
                p2.setSpacingAfter(1);//no alignment
            }
            document.add(p2);

            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
