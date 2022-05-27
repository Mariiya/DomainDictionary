package com.pullenti.domaindictionary.webapi;

import com.pullenti.domaindictionary.model.DictionaryEntry;

public interface InternetResourceSearchAPI {
     DictionaryEntry search(String term, String language) throws Exception;
}
