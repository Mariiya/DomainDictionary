package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryEntryDao;
import com.domaindictionary.model.elasticsearch.DictionaryEntryDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionarySearchService {

    private final DictionaryEntryDao dictionaryEntryDao;

    public List<DictionaryEntryDoc> search(List<String> terms) throws IOException {
        return dictionaryEntryDao.search(terms);
    }
}
