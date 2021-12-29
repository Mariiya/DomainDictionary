package com.domaindictionary.controllers;

import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.service.DictionaryBank;
import com.domaindictionary.service.DictionaryService;
import com.domaindictionary.model.DomainDictionary;
import com.domaindictionary.service.DomainAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchManager {
    @Autowired
    private DictionaryBank bank;
    @Autowired
    DictionaryService dictionaryService;
    @Autowired
    private DomainAnalyzerService analyzerService;

    @GetMapping()
    public List<DictionaryEntry> search(@RequestParam List<String> terms, @RequestParam BigInteger resourceId) {
        SearchResource searchResource = bank.getResource(resourceId);
        return dictionaryService.search(terms, searchResource);
    }

    public void createDomainDictionary(List<DictionaryEntry> entries) {
        new DomainDictionary();
    }

    @GetMapping("/resources")
    public Collection<SearchResource> getResources() {
        return bank.getResources();
    }

}
