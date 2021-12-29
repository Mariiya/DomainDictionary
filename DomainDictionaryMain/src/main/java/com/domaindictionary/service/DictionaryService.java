package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.model.ElectronicDictionary;
import com.domaindictionary.model.InternetResource;
import com.domaindictionary.model.SearchResource;
import com.domaindictionary.service.parser.ParseDictionaryFileToDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DictionaryService {
    private final DictionaryDao dictionaryDao;
    private final ParseDictionaryFileToDB parser;

    @Autowired
    public DictionaryService(DictionaryDao dictionaryDao, ParseDictionaryFileToDB parser) {
        this.dictionaryDao = dictionaryDao;
        this.parser = parser;
     //   initialize(new ElectronicDictionary(BigInteger.ONE,"Ozhegov", "Ozhegov", "src/main/resources/dictionaries/ozhegov.txt",
    //            null, null, new Rule(BigInteger.TWO,"</k>",",", "1.","null"),null));
    }

    public List<DictionaryEntry> search(List<String> terms, SearchResource resource) {
        List<DictionaryEntry> result = new ArrayList<>();
        SearchService searchService;
        if (resource instanceof InternetResource) {
            searchService = new InternetResourceSearchService(dictionaryDao);
        } else {
            searchService = new ElectronicDictionarySearchService(dictionaryDao);
        }

        for (String term : terms) {
            result.add(searchService.search(term,resource));
        }
        return result;
    }

    public void initialize(ElectronicDictionary dictionary) {
        this.parser.parse(dictionary, dictionaryDao);
    }

}
