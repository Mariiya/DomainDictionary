package com.onpu.domaindictionary.service;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import com.onpu.domaindictionary.model.DictionaryEntry;
import com.onpu.domaindictionary.model.DomainDictionary;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class DomainDictionaryService {

    private final String SAVE_TO = "src/main/resources/domainDictionaries/";
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    public ByteArrayInputStream createDomainDictionary() {
        Document document = new Document();
        try {

            PdfWriter.getInstance(document, out);
            //open
            document.open();
            Paragraph p = new Paragraph();
            p.add("This is my paragraph 1");
            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);

            Paragraph p2 = new Paragraph();
            p2.add("This is my paragraph 2"); //no alignment

            document.add(p2);

            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(8);

            document.add(new Paragraph("This is my paragraph 3", f));

            //close
            document.close();
            System.out.println("Done");
            return new ByteArrayInputStream(out.toByteArray());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

}
