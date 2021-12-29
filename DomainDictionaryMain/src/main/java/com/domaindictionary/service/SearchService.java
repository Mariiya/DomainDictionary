package com.domaindictionary.service;

import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.model.SearchResource;

public interface SearchService {
    DictionaryEntry search (String term, SearchResource resource);

}
