package com.domaindictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainAnalyzer {

    private final ClaudeApiService claudeApiService;

    public List<String> filterDefinitionsByDomain(String term, List<String> definitions, String domainContext) {
        return claudeApiService.filterDefinitionsByDomain(term, definitions, domainContext);
    }

    public List<String> findSynonyms(String term) {
        return claudeApiService.findSynonyms(term);
    }
}
