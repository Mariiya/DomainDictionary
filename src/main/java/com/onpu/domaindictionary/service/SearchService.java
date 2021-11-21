package com.onpu.domaindictionary.service;

import com.onpu.domaindictionary.model.DictionaryEntry;
import com.onpu.domaindictionary.model.SearchResource;

public interface SearchService {
    DictionaryEntry search (String term, SearchResource resource);

}
