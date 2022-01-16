package com.domaindictionary.service;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;

public interface SearchService {
    DictionaryEntry search (String term, SearchResource resource);
}
