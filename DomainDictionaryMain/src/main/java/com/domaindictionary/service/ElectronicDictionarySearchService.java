package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
import org.springframework.stereotype.Service;

@Service
public class ElectronicDictionarySearchService implements SearchService {

    private final DictionaryDao dictionaryDao;

    public ElectronicDictionarySearchService(DictionaryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }

    public DictionaryEntry search(String term, SearchResource resource) {
        return dictionaryDao.search(term, resource.getId());
    }
}
