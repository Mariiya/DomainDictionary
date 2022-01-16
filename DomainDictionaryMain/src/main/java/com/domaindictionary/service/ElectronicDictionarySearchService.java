package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.elasticsearch.api.SearchManager;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
import org.springframework.stereotype.Service;

@Service
public class ElectronicDictionarySearchService implements SearchService {

    private final DictionaryDao dictionaryDao;
    private final SearchManager searchManager;


    public ElectronicDictionarySearchService(DictionaryDao dictionaryDao, SearchManager searchManager) {
        this.dictionaryDao = dictionaryDao;
        this.searchManager = searchManager;
    }

    public DictionaryEntry search(String term, SearchResource resource) {
        return searchManager.search(term, resource.getId());
    }
}
