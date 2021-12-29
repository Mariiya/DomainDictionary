package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
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
