package com.domaindictionary.webapi;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;

public interface InternetResourceSearchAPI {
     DictionaryEntry search(String term, String language) throws Exception;
}
