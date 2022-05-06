package com.domaindictionary.service;

import com.domaindictionary.model.DictionaryEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;


@Service("searchProcessManager")
public class SearchManager {
    private final ElectronicDictionarySearchService electronicDictionarySearchService;
    private final InternetResourceSearchService internetResourceSearchService;
    private final DomainAnalysisService analysisService;

    @Autowired
    public SearchManager(ElectronicDictionarySearchService electronicDictionarySearchService, InternetResourceSearchService internetResourceSearchService, DomainAnalysisService analysisService) {
        this.electronicDictionarySearchService = electronicDictionarySearchService;
        this.internetResourceSearchService = internetResourceSearchService;
        this.analysisService = analysisService;
    }

    public List<DictionaryEntry> search(List<String> terms, Map<String, Object> params) throws Exception {
        Collection<String> searchMarkers = terms;
        if (params.containsKey(Constants.SEARCH_MARKERS)) {
            searchMarkers.addAll((Collection<String>) params.get(Constants.SEARCH_MARKERS));
        }
        SearchService searchService;
        searchService = electronicDictionarySearchService;

        List<DictionaryEntry> result = new ArrayList<>();
        if (terms.size() == 1) {
            result.add(searchService.search(terms.get(0), params));
        } else {
            result.addAll(searchService.search(terms, params));
        }

        addEmptyDefinitionTerms(terms, result);
        //Check For Internet Search
        for (DictionaryEntry dictionaryEntry : result) {
            if (dictionaryEntry.getDefinition().isEmpty() && Boolean.parseBoolean((String) params.get(Constants.IS_SEARCH_IN_INTERNET))) {
                searchService = internetResourceSearchService;
                dictionaryEntry.setDefinition(
                        searchService.search(dictionaryEntry.getTerm(), params).getDefinition());
            }
        }
        //Check for full text search

        //Analyze results
        if (Boolean.parseBoolean((String) params.get(Constants.IS_DOMAIN_ANALYZE))) {
            analysisService.analyze(result, searchMarkers);
        }
        return result;
    }

    protected void addEmptyDefinitionTerms(List<String> terms, List<DictionaryEntry> dictionaryEntries) {
        for (String t : terms) {
            boolean isDe = false;
            for (DictionaryEntry de : dictionaryEntries) {
                if (de.getTerm().equalsIgnoreCase(t)) {
                    isDe = true;
                    break;
                }
            }
            if (!isDe) {
                dictionaryEntries.add(new DictionaryEntry(null, t, Collections.emptyList(), BigInteger.ONE));
            }
        }
    }

}
