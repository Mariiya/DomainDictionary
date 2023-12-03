package com.domaindictionary.service;


import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.dao.DictionaryEntryDao;
import com.domaindictionary.model.*;
import com.domaindictionary.model.enumeration.ResourceType;
import com.domaindictionary.service.parser.DictionaryParser;
import com.domaindictionary.utils.RegexConstants;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class ResourceService {
    private static final Logger LOG = Logger.getLogger(ResourceService.class);
    private final DictionaryDao dictionaryDao;
    private final DictionaryEntryDao dictionaryEntryDao;
    private final FileService filedService;
    private final DictionaryParser parser;
    private ByteArrayOutputStream out;

    public File saveResource(InternalResource resource, User user) throws DocumentException, IOException {
        ByteArrayInputStream document = null;
        if (resource instanceof ElectronicDictionary) {
            if (((ElectronicDictionary) resource).getType() == ResourceType.GLOSSARY) {
                document = createGlossary((ElectronicDictionary) resource, user);
            } else {
                document = createDomainDictionary((ElectronicDictionary) resource, user);
            }
        } else if (resource instanceof Thesaurus) {
            document = createThesaurus((Thesaurus) resource, user);
        }

        String path = filedService.saveToFile(document, user.getName());
        resource.setPathToFile(path);
        saveResource(resource, document);
        return filedService.extractFile(path);
    }

    private void saveResource(InternalResource r, ByteArrayInputStream document) throws IOException {
        String generatedFilePath = filedService.createAndSaveFile(document, r.getPathToFile());
        if (!generatedFilePath.isEmpty()) {
            r.setPathToFile(generatedFilePath);
            for (InternalResource resource : dictionaryDao.getResources()) {
                if (resource != null && resource.getName() != null
                        && resource.getName().equals(r.getName())) {
                    r.setName(r.getName() + "_" + r.getId());
                }
            }
            dictionaryDao.createDictionary(r);
            if (r instanceof ElectronicDictionary
                    && ((ElectronicDictionary) r).getType() == ResourceType.SYSTEM_GENERAL) {
                parser.parse((ElectronicDictionary) r);
            }
        }
    }

    public Collection<String> getPossibleRelators() {
        return RegexConstants.getTemplatesForRelator();
    }

    public Collection<String> getPossibleArticleSeparator() {
        return RegexConstants.getTemplatesForArticleSeparator();
    }

    private ByteArrayInputStream createGlossary(ElectronicDictionary dictionary, User user) {
        return createGlossary(dictionary, user);
    }

    private ByteArrayInputStream createThesaurus(Thesaurus thesaurus, User user) throws DocumentException, IOException {
        Document document = getDocumentWithHeading("Thesaurus");
        String FONT_MAIN = "/src/main/resources/fonts/arial.ttf";
        BaseFont bf = BaseFont.createFont(FONT_MAIN, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font fontTerm = new Font(bf, 14, Font.BOLD);
        Font fontDefinition = new Font(bf, 14, Font.NORMAL);
        Paragraph p2 = new Paragraph();
        for (ThesaurusEntry entry : thesaurus.getEntries()) {
            p2.setFont(fontTerm);
            p2.add(entry.getTerm());
            p2.setFont(fontDefinition);
            p2.add(" - " + entry.getRelations() + '\n');
            p2.setSpacingAfter(1);//no alignment
        }
        document.add(p2);

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }


    private ByteArrayInputStream createDomainDictionary(ElectronicDictionary dictionary, User user) throws DocumentException, IOException {
        Document document = getDocumentWithHeading("Domain Dictionary");
        String FONT_MAIN = "/src/main/resources/fonts/arial.ttf";
        BaseFont bf = BaseFont.createFont(FONT_MAIN, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font fontTerm = new Font(bf, 14, Font.BOLD);
        Font fontDefinition = new Font(bf, 14, Font.NORMAL);

        Paragraph p2 = new Paragraph();
        for (DictionaryEntry entry : dictionary.getEntries()) {
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

    private Document getDocumentWithHeading(String title) throws DocumentException, IOException {
        Document document = new Document();
        String FONT_MAIN = "/src/main/resources/fonts/arial.ttf";
        BaseFont bf = BaseFont.createFont(FONT_MAIN, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontTitle = new Font(bf, 16, Font.NORMAL);

        PdfWriter.getInstance(document, out);
        document.open();
        Paragraph p = new Paragraph();
        p.setFont(fontTitle);
        p.add(title + " \n" + '\n');
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        return document;
    }

    public File editDictionary(InternalResource resource) throws DocumentException, IOException {
        dictionaryDao.updateDictionary(resource);
        dictionaryEntryDao.updateEntries(resource.getEntries());
        return saveResource(resource, resource.getUser());
    }

    public void deleteNewDictionary(String resourceId) {
        dictionaryDao.deleteDictionary(resourceId);
        dictionaryEntryDao.removeEntries(resourceId);
    }

    public void deleteDictionary(String resourceId, User user) {
        deleteNewDictionary(resourceId);
        filedService.removeFile(getResource(resourceId).getPathToFile());
    }

    public InternalResource getResource(String resourceId) {
        return dictionaryDao.getResource(resourceId);
    }
}
