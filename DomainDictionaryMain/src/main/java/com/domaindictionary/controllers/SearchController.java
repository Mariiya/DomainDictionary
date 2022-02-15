package com.domaindictionary.controllers;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.service.ResourcesBank;
import com.domaindictionary.service.DictionaryService;
import com.domaindictionary.model.DomainDictionary;
import com.domaindictionary.service.DomainAnalysisService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final ResourcesBank bank;
    private final DictionaryService dictionaryService;
    private final DomainAnalysisService analyzerService;

    public SearchController(DictionaryService dictionaryService, ResourcesBank bank, DomainAnalysisService analyzerService) {
        this.dictionaryService = dictionaryService;
        this.bank = bank;
        this.analyzerService = analyzerService;
    }

    @GetMapping()
    public List<DictionaryEntry> search(@RequestParam List<String> terms, @RequestParam BigInteger resourceId) throws IOException {
        SearchResource searchResource = bank.getResource(resourceId);
        return dictionaryService.search(terms, searchResource);
    }

    public void createDomainDictionary(List<DictionaryEntry> entries) {
        new DomainDictionary();
    }

}
