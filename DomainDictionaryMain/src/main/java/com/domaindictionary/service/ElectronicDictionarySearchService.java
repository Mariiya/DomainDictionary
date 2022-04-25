package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.elasticsearch.api.SearchManager;
import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.Rule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Service
public class ElectronicDictionarySearchService implements SearchService {

    private final DictionaryDao dictionaryDao;
    private final SearchManager searchManager;


    public ElectronicDictionarySearchService(DictionaryDao dictionaryDao, SearchManager searchManager) {
        this.dictionaryDao = dictionaryDao;
        this.searchManager = searchManager;
    }

    public DictionaryEntry search(String term, Map<String, Object> params) throws IOException {
        return searchManager.search(term, new BigInteger((String) params.get("resourceId")));
    }

    public Collection<DictionaryEntry> search(Collection<String> term, Map<String, Object> params) throws IOException {
        Collection<DictionaryEntry> result = searchManager.search(term, new BigInteger((String) params.get("resourceId")));
        extractDefinitions(result);
        return result;
    }

    protected void extractDefinitions(Collection<DictionaryEntry> dictionaryEntries) {
        ElectronicDictionary electronicDictionary = dictionaryDao.getElectronicDictionary(dictionaryEntries.iterator().next().getResourceId());
        if (electronicDictionary != null) {
            Rule rule = electronicDictionary.getRule();

            for (DictionaryEntry de : dictionaryEntries) {
                if (de.getDefinition().size() == 1) {
                    String definition = de.getDefinition().iterator().next();
                    String[] definitions = definition.split("\\d{1,2}\\.");//rule.getDefinitionSeparator());
                    List<String> toList = Arrays.asList(definitions);
                    if (toList.size() > 2) {
                        toList = toList.subList(1, toList.size());
                    }
                    de.setDefinition(toList);
                }
            }
        }
    }
}
