package com.domaindictionary.controllers;

import com.domaindictionary.model.Entry;
import com.domaindictionary.model.ThesaurusEntry;
import com.domaindictionary.service.SearchOrchestrator;
import com.domaindictionary.model.DictionaryEntry;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchOrchestrator searchOrchestrator;

    public SearchController(SearchOrchestrator searchOrchestrator) {
        this.searchOrchestrator = searchOrchestrator;
    }

    @GetMapping()
    public Collection<DictionaryEntry> search(@RequestParam List<String> terms, @RequestParam boolean isAnalyzeEnable) throws Exception {
        return searchOrchestrator.search(terms, isAnalyzeEnable);
    }

    @GetMapping()
    public Collection<ThesaurusEntry> searchRelations(@RequestParam List<String> terms) {
        return searchOrchestrator.searchRelations(terms);
    }


}
