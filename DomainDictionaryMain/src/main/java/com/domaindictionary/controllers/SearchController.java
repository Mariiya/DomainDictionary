package com.domaindictionary.controllers;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.service.SearchManager;
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
    private final SearchManager searchManager;
    private final DomainAnalysisService analyzerService;

    public SearchController(SearchManager searchManager, ResourcesBank bank, DomainAnalysisService analyzerService) {
        this.searchManager = searchManager;
        this.bank = bank;
        this.analyzerService = analyzerService;
    }

    @GetMapping()
    public List<DictionaryEntry> search(@RequestParam List<String> terms, @RequestParam Map<String,Object> params) throws IOException {
        BigInteger resourceId = new BigInteger((String) params.get("resourceId"));
        SearchResource searchResource = bank.getResource(resourceId);
        params.put("searchResource", searchResource);
        return searchManager.search(terms, params);
    }


}
