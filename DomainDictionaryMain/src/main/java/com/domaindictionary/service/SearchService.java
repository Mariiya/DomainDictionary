package com.domaindictionary.service;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;

import java.io.IOException;

public interface SearchService {
    DictionaryEntry search (String term, SearchResource resource) throws IOException;
}
