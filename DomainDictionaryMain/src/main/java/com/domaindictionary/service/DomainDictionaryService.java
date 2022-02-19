package com.domaindictionary.service;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.itextpdf.text.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class DomainDictionaryService {
private  static final Logger LOG = Logger.getLogger(DomainDictionaryService.class);
    @Autowired
    private FileUploadService fileUploadService;

    public ByteArrayInputStream createDomainDictionary(List<DictionaryEntry> entries) {
        try {
         return  fileUploadService.createFileForDomainDictionary(entries);
        } catch (DocumentException | IOException e) {
            LOG.error(e.getMessage(),e);
        }
        throw new RuntimeException("Error during file creation");
    }

}
