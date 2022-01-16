package com.domaindictionary.service;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.itextpdf.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class DomainDictionaryService {

    @Autowired
    private FileUploadService fileUploadService;

    public ByteArrayInputStream createDomainDictionary(List<DictionaryEntry> entries) {
        try {
            fileUploadService.createFileForDomainDictionary(entries);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
