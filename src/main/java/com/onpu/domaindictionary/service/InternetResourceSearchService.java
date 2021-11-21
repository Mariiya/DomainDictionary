package com.onpu.domaindictionary.service;

import com.onpu.domaindictionary.dao.DictionaryDao;
import com.onpu.domaindictionary.model.DictionaryEntry;
import com.onpu.domaindictionary.model.SearchResource;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

@Service
public class InternetResourceSearchService implements SearchService {
    private final DictionaryDao dictionaryDao;

    public InternetResourceSearchService(DictionaryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }

    public DictionaryEntry search(String term, SearchResource resource) {
        //not implemented
        return new DictionaryEntry(new BigInteger("2"),".",new ArrayList<>());
    }
}
