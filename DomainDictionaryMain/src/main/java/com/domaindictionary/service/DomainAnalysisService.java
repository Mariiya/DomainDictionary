package com.domaindictionary.service;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.domaindictionary.model.DomainDictionary;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class DomainAnalysisService {

    public DomainDictionary analyze(DomainDictionary dd, Collection<String> searchTerms) {
        for (DictionaryEntry de : dd.getEntries()) {
            filterDictionaryEntry(de, searchTerms);
        }
        return dd;
    }

    public DomainDictionary analyze(DomainDictionary dd, String stylisticZone) {
        return dd;
    }

    protected void filterDictionaryEntry(DictionaryEntry entry, Collection<String> searchTerms) {
        Map<String, Integer> definitionToCount = new HashMap<>();
        for (String definition : entry.getDefinition()) {
            int count = getNumberOfMatches(definition, searchTerms);
            definitionToCount.put(definition, count);
        }

    }

    private int getNumberOfMatches(String definition, Collection<String> searchTerms) {
        int count = 0;
        String[] bagOfWords = definition.split(" ");
        for (String word : bagOfWords) {
            if (isMatch(word, searchTerms)) {
                count++;
            }
        }
        return count;
    }

    private boolean isMatch(String word, Collection<String> searchTerms) {
        for (String term : searchTerms) {
            if (word != null) {
                return word.equals(term) || word.contains(term);
            }
        }
        return false;
    }
}
