package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.webapi.InternetResourceSearchAPI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class InternetResourceSearchService implements SearchService {
    private static final Logger LOG = Logger.getLogger(InternetResourceSearchService.class);
    final ApplicationContext applicationContext;

    private final DictionaryDao dictionaryDao;

    public InternetResourceSearchService(DictionaryDao dictionaryDao, ApplicationContext applicationContext) {
        this.dictionaryDao = dictionaryDao;
        this.applicationContext = applicationContext;
    }

    public DictionaryEntry search(String term, Map<String, Object> params) {
        System.out.println(Constants.internetResources);
        System.out.println(Constants.internetResources.get(Constants.WIKIPEDIA));
        InternetResourceSearchAPI searchResource = applicationContext.getBean(Constants.internetResources.get(Constants.WIKIPEDIA));
        try {
            return searchResource.search(term, (String) params.get("language"));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new DictionaryEntry();
    }

    @Override
    public Collection<DictionaryEntry> search(Collection<String> terms,  Map<String, Object> params) throws IOException {
        InternetResourceSearchAPI searchResource = applicationContext.getBean(Constants.internetResources.get(Constants.WIKIPEDIA));
        List<DictionaryEntry> entryCollections = new ArrayList<>();
        for(String term: terms){
            try {
                entryCollections.add(searchResource.search(term,(String) params.get("language")));
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return entryCollections;
    }
}