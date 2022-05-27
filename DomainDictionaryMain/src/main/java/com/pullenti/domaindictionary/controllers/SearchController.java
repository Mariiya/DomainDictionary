package com.pullenti.domaindictionary.controllers;

import com.pullenti.domaindictionary.model.DictionaryEntry;
import com.pullenti.domaindictionary.model.SearchResource;
import com.pullenti.domaindictionary.service.SearchManager;
import com.pullenti.domaindictionary.service.ResourcesBank;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final ResourcesBank bank;
    private final SearchManager searchManager;

    public SearchController(SearchManager searchManager, ResourcesBank bank) {
        this.searchManager = searchManager;
        this.bank = bank;
    }

    @GetMapping()
    public List<DictionaryEntry> search(@RequestParam List<String> terms, @RequestParam Map<String,Object> params) throws Exception {
        BigInteger resourceId = new BigInteger((String) params.get("resourceId"));
        SearchResource searchResource = bank.getResource(resourceId);
        params.put("searchResource", searchResource);
        return searchManager.search(terms, params);
    }


}
