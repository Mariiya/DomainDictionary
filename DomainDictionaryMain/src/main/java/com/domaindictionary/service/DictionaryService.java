package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.InternetResource;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.service.parser.ParseDictionaryFileToStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class DictionaryService {
    private final DictionaryDao dictionaryDao;
    private final ParseDictionaryFileToStorage parser;
    private final ElectronicDictionarySearchService electronicDictionarySearchService;
    private final InternetResourceSearchService internetResourceSearchService;

    @Autowired
    public DictionaryService(DictionaryDao dictionaryDao, ParseDictionaryFileToStorage parser, ElectronicDictionarySearchService electronicDictionarySearchService, InternetResourceSearchService internetResourceSearchService) {
        this.dictionaryDao = dictionaryDao;
        this.parser = parser;
        this.electronicDictionarySearchService = electronicDictionarySearchService;
        this.internetResourceSearchService = internetResourceSearchService;
    }

    public List<DictionaryEntry> search(List<String> terms, SearchResource resource) throws IOException {
        List<DictionaryEntry> result = new ArrayList<>();
        SearchService searchService;
        if (resource instanceof InternetResource) {
            searchService = internetResourceSearchService;
        } else {
            searchService = electronicDictionarySearchService;
        }

        for (String term : terms) {
            result.add(searchService.search(term, resource));
        }
        return result;
    }

}
