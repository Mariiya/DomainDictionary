package com.domaindictionary.webapi;

import com.domaindictionary.model.DictionaryEntry;

public interface InternetResourceSearchAPI {
     DictionaryEntry search(String term, String language) throws Exception;
}
