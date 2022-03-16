package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.service.parser.ParseDictionaryFileToStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class DictionaryService {
    private final DictionaryDao dictionaryDao;
    private final ElectronicDictionarySearchService electronicDictionarySearchService;
    private final InternetResourceSearchService internetResourceSearchService;

    @Autowired
    public DictionaryService(DictionaryDao dictionaryDao, ElectronicDictionarySearchService electronicDictionarySearchService, InternetResourceSearchService internetResourceSearchService) {
        this.dictionaryDao = dictionaryDao;
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
        if (Boolean.parseBoolean((String) params.get("isSearchInInternet"))) {
            searchService = internetResourceSearchService;
            for (DictionaryEntry entry : result) {
                if (entry.getDefinition().isEmpty()) {
                    entry.setDefinition(searchService.search(entry.getTerm(),params).getDefinition());
                }
            }
        }
        return result;
    }

}
