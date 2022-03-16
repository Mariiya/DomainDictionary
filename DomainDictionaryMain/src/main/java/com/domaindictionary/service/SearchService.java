package com.domaindictionary.service;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface SearchService {
    DictionaryEntry search (String term, Map<String, Object> params) throws IOException;
    Collection<DictionaryEntry> search (Collection<String> terms, Map<String, Object> params) throws IOException;
}
