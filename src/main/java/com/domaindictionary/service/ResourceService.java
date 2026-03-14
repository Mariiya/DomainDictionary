package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryEntryDao;
import com.domaindictionary.dto.SaveResourceRequest;
import com.domaindictionary.dto.SearchResult;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.Rule;
import com.domaindictionary.model.User;
import com.domaindictionary.model.enumeration.ResourceType;
import com.domaindictionary.repository.ElectronicDictionaryRepository;
import com.domaindictionary.repository.UserRepository;
import com.domaindictionary.service.parser.DictionaryParser;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ElectronicDictionaryRepository dictionaryRepository;
    private final UserRepository userRepository;
    private final DictionaryParser dictionaryParser;
    private final DictionaryEntryDao dictionaryEntryDao;

    public byte[] generateDocument(SaveResourceRequest request, Long userId) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfDocument pdf = new PdfDocument(new PdfWriter(out));
            Document document = new Document(pdf);

            PdfFont font;
            try {
                font = PdfFontFactory.createFont("src/main/resources/fonts/arial.ttf",
                        com.itextpdf.io.font.PdfEncodings.IDENTITY_H);
            } catch (Exception e) {
                font = PdfFontFactory.createFont();
            }

            // Title
            String title = switch (request.getType()) {
                case GLOSSARY -> "Glossary";
                case DOMAIN -> "Domain Dictionary";
                default -> "Dictionary";
            };

            document.add(new Paragraph(title)
                    .setFont(font).setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            // Entries
            for (SearchResult entry : request.getEntries()) {
                Paragraph p = new Paragraph();
                p.add(new Text(entry.getTerm()).setFont(font).setFontSize(14).setBold());
                if (entry.getDefinitions() != null && !entry.getDefinitions().isEmpty()) {
                    p.add(new Text(" — " + String.join("; ", entry.getDefinitions()))
                            .setFont(font).setFontSize(12));
                }
                document.add(p);
            }

            document.close();

            // Save to DB
            User user = userRepository.findById(userId).orElse(null);
            ElectronicDictionary dict = ElectronicDictionary.builder()
                    .name(request.getName())
                    .language(request.getLanguage())
                    .type(request.getType())
                    .createdBy(user)
                    .build();
            dictionaryRepository.save(dict);

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    public void uploadAndParseDictionary(MultipartFile file, String name, String language, Long userId,
                                         String articleSeparator, String termSeparator, String definitionSeparator) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rule rule = Rule.builder()
                .articleSeparator(articleSeparator)
                .termSeparator(termSeparator)
                .definitionSeparator(definitionSeparator)
                .build();

        ElectronicDictionary dict = ElectronicDictionary.builder()
                .name(name)
                .language(language)
                .type(ResourceType.SYSTEM_GENERAL)
                .createdBy(user)
                .rule(rule)
                .build();
        dict = dictionaryRepository.save(dict);

        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        dictionaryParser.parseAndLoad(content, dict);
    }
}
