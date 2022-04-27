package com.domaindictionary.controllers;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.service.DictionaryManager;
import com.domaindictionary.service.ResourcesBank;
import com.domaindictionary.service.DomainAnalysisService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final ResourcesBank bank;
    private final DictionaryManager dictionaryManager;
    private final DomainAnalysisService analyzerService;

    public SearchController(DictionaryManager dictionaryManager, ResourcesBank bank, DomainAnalysisService analyzerService) {
        this.dictionaryManager = dictionaryManager;
        this.bank = bank;
        this.analyzerService = analyzerService;
    }

    @GetMapping()
    public List<DictionaryEntry> search(@RequestParam List<String> terms, @RequestParam Map<String,Object> params) throws IOException {
        BigInteger resourceId = new BigInteger((String) params.get("resourceId"));
        SearchResource searchResource = bank.getResource(resourceId);
        params.put("searchResource", searchResource);
        return dictionaryManager.search(terms, params);
    }


}
