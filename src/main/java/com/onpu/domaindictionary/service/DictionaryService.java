package com.onpu.domaindictionary.service;

import com.onpu.domaindictionary.dao.DictionaryDao;
import com.onpu.domaindictionary.model.DictionaryEntry;
import com.onpu.domaindictionary.model.ElectronicDictionary;
import com.onpu.domaindictionary.model.SearchResource;
import com.onpu.domaindictionary.service.parser.ParseDictionaryFileToDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class DictionaryService {
    private final DictionaryDao dictionaryDao;
    private final ParseDictionaryFileToDB parser;

    @Autowired
    public DictionaryService(DictionaryDao dictionaryDao, ParseDictionaryFileToDB parser) {
        this.dictionaryDao = dictionaryDao;
        this.parser = parser;
    }

    public List<DictionaryEntry> search(List<String> terms, SearchResource resource){
        List<DictionaryEntry> result = new ArrayList<>();
        for(String term:terms){
            result.add(dictionaryDao.search(term,resource.getId()));
        }
        return result;
    }
    public void initialize(ElectronicDictionary dictionary) {
        this.parser.parse(dictionary,dictionaryDao);
    }

}
