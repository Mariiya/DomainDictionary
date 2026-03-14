package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryEntryDao;
import com.domaindictionary.dto.SearchResult;
import com.domaindictionary.model.elasticsearch.DictionaryEntryDoc;
import com.domaindictionary.model.elasticsearch.ThesaurusEntryDoc;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchOrchestrator {
    private static final Logger log = LoggerFactory.getLogger(SearchOrchestrator.class);

    private final DictionarySearchService dictionarySearchService;
    private final ExternalResourceSearchService externalResourceSearchService;
    private final DomainAnalyzer domainAnalyzer;

    public List<SearchResult> search(List<String> terms, boolean analyzeEnabled, String domainContext) {
        List<SearchResult> results = new ArrayList<>();

        for (String term : terms) {
            SearchResult result = searchTerm(term.trim());
            results.add(result);
        }

        // Split multi-value definitions
        for (SearchResult r : results) {
            if (r.getDefinitions() != null && r.getDefinitions().size() == 1) {
                String def = r.getDefinitions().get(0);
                String[] parts = def.split("\\s+\\d{1,2}\\.");
                if (parts.length > 1) {
                    List<String> split = Arrays.stream(parts)
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList());
                    r.setDefinitions(split);
                }
            }
        }

        // Domain analysis — filter polysemous terms
        if (analyzeEnabled) {
            List<String> allTerms = results.stream().map(SearchResult::getTerm).toList();
            String context = domainContext != null ? domainContext : String.join(", ", allTerms);
            for (SearchResult r : results) {
                if (r.getDefinitions() != null && r.getDefinitions().size() > 1) {
                    List<String> filtered = domainAnalyzer.filterDefinitionsByDomain(
                            r.getTerm(), r.getDefinitions(), context);
                    r.setDefinitions(filtered);
                }
            }
        }

        return results;
    }

    private SearchResult searchTerm(String term) {
        // 1. Search internal dictionaries (ES)
        try {
            List<DictionaryEntryDoc> found = dictionarySearchService.search(List.of(term));
            if (!found.isEmpty()) {
                DictionaryEntryDoc entry = found.get(0);
                return SearchResult.builder()
                        .term(term)
                        .definitions(entry.getDefinitions())
                        .source("internal")
                        .build();
            }
        } catch (Exception e) {
            log.warn("Internal search failed for term '{}': {}", term, e.getMessage());
        }

        // 2. For multi-word terms, try subterms
        if (term.contains(" ")) {
            try {
                String[] words = term.split("\\s+");
                for (String word : words) {
                    List<DictionaryEntryDoc> found = dictionarySearchService.search(List.of(word));
                    if (!found.isEmpty()) {
                        return SearchResult.builder()
                                .term(term)
                                .definitions(found.get(0).getDefinitions())
                                .source("internal-subterm")
                                .build();
                    }
                }
            } catch (Exception e) {
                log.warn("Subterm search failed for '{}': {}", term, e.getMessage());
            }
        }

        // 3. External resources (Wikipedia → Claude API)
        try {
            SearchResult external = externalResourceSearchService.search(term);
            if (external != null && external.getDefinitions() != null && !external.getDefinitions().isEmpty()) {
                return external;
            }
        } catch (Exception e) {
            log.warn("External search failed for '{}': {}", term, e.getMessage());
        }

        // 4. Nothing found
        return SearchResult.builder()
                .term(term)
                .definitions(Collections.emptyList())
                .source("not_found")
                .build();
    }

    public List<ThesaurusEntryDoc> searchRelations(List<String> terms) {
        List<ThesaurusEntryDoc> results = new ArrayList<>();
        for (String term : terms) {
            List<String> synonyms = domainAnalyzer.findSynonyms(term);
            ThesaurusEntryDoc entry = ThesaurusEntryDoc.builder()
                    .term(term)
                    .relations(List.of(ThesaurusEntryDoc.Relation.builder()
                            .synsets(synonyms)
                            .type(com.domaindictionary.model.enumeration.RelationType.SYNONYM)
                            .coef(1.0)
                            .build()))
                    .build();
            results.add(entry);
        }
        return results;
    }
}
