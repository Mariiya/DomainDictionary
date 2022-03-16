package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.elasticsearch.api.SearchManager;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

@Service
public class ElectronicDictionarySearchService implements SearchService {

    private final DictionaryDao dictionaryDao;
    private final SearchManager searchManager;


    public ElectronicDictionarySearchService(DictionaryDao dictionaryDao, SearchManager searchManager) {
        this.dictionaryDao = dictionaryDao;
        this.searchManager = searchManager;
    }

    public DictionaryEntry search(String term, Map<String,Object> params) throws IOException {
        return searchManager.search(term, new BigInteger((String) params.get("resourceId")));
    }

    public Collection<DictionaryEntry> search(Collection<String> term,  Map<String,Object> params) throws IOException {
        return searchManager.search(term, new BigInteger((String) params.get("resourceId")));
    }
}
