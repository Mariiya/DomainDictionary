package com.domaindictionary.controller;

import com.domaindictionary.dto.SearchResult;
import com.domaindictionary.model.elasticsearch.ThesaurusEntryDoc;
import com.domaindictionary.service.SearchOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchOrchestrator searchOrchestrator;

    @GetMapping
    public ResponseEntity<List<SearchResult>> search(
            @RequestParam List<String> terms,
            @RequestParam(defaultValue = "false") boolean analyzeEnabled,
            @RequestParam(required = false) String domainContext) {
        List<SearchResult> results = searchOrchestrator.search(terms, analyzeEnabled, domainContext);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/relations")
    public ResponseEntity<List<ThesaurusEntryDoc>> searchRelations(@RequestParam List<String> terms) {
        return ResponseEntity.ok(searchOrchestrator.searchRelations(terms));
    }
}
