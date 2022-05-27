package com.pullenti.domaindictionary.service;

import com.pullenti.domaindictionary.model.DictionaryEntry;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileUploadService {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    ByteArrayInputStream createFileForDomainDictionary(List<DictionaryEntry> entries) throws DocumentException, IOException {
        Document document = new Document();
        String FONT_MAIN = "DomainDictionaryMain/src/main/resources/fonts/arial.ttf";
        BaseFont bf = BaseFont.createFont(FONT_MAIN, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font fontTerm = new Font(bf, 14, Font.BOLD);
        Font fontDefinition = new Font(bf, 14, Font.NORMAL);
        Font fontTitle = new Font(bf, 16, Font.NORMAL);

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
    }

    public String saveFile(MultipartFile file, String filePath) {
        File fileToSave = new File("DomainDictionaryMain\\src\\main\\resources\\dictionaries\\" + file.getOriginalFilename());

        try (OutputStream os = new FileOutputStream(fileToSave)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Files.exists(Paths.get(fileToSave.getAbsolutePath()))) {
            return fileToSave.getAbsolutePath();
        }
        return "";
    }

    public String saveToFile(ByteArrayInputStream stream, String name) {
        File fileToSave = new File("DomainDictionaryMain\\src\\main\\resources\\dictionaries\\"
                + name + System.currentTimeMillis() + ".txt");

        try {
            IOUtils.copy(stream, new FileOutputStream(fileToSave));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Files.exists(Paths.get(fileToSave.getAbsolutePath()))) {
            return fileToSave.getAbsolutePath();
        }
        return "";
    }
}
