package com.onpu.domaindictionary.controllers;

import com.onpu.domaindictionary.model.DictionaryEntry;
import com.onpu.domaindictionary.model.DomainDictionary;
import com.onpu.domaindictionary.model.SearchResource;
import com.onpu.domaindictionary.service.DictionaryBank;
import com.onpu.domaindictionary.service.DictionaryService;
import com.onpu.domaindictionary.service.DomainAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
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
