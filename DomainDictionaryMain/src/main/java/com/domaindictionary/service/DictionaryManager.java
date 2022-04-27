package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class DictionaryManager {
    private final ElectronicDictionarySearchService electronicDictionarySearchService;
    private final InternetResourceSearchService internetResourceSearchService;

    @Autowired
    public DictionaryManager(ElectronicDictionarySearchService electronicDictionarySearchService, InternetResourceSearchService internetResourceSearchService) {
        this.electronicDictionarySearchService = electronicDictionarySearchService;
        this.internetResourceSearchService = internetResourceSearchService;
    }

    public List<DictionaryEntry> search(List<String> terms, Map<String, Object> params) throws IOException {
        SearchService searchService;
        searchService = electronicDictionarySearchService;

        List<DictionaryEntry> result = new ArrayList<>();
        if (terms != null && terms.size() == 1) {
            result.add(searchService.search(terms.get(0), params));
        } else {
            result.addAll(searchService.search(terms, params));
        }
        for (String t : terms) {
            boolean isDe = false;
            for (DictionaryEntry de : result) {
                if (de.getTerm().equalsIgnoreCase(t)) {
                    isDe = true;
                    break;
                }
            }
            if (!isDe) {
                result.add(new DictionaryEntry(null, t, Collections.emptyList(), BigInteger.ONE));
            }
        }
        for (DictionaryEntry dictionaryEntry : result) {
            if (dictionaryEntry.getDefinition().isEmpty() && Boolean.parseBoolean((String) params.get("isSearchInInternet"))) {
                searchService = internetResourceSearchService;
                dictionaryEntry.setDefinition(
                        searchService.search(dictionaryEntry.getTerm(), params).getDefinition());
            }
        }
        return result;
    }

}
