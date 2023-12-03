package com.domaindictionary.service;


import com.domaindictionary.dao.DictionaryEntryDao;
import com.domaindictionary.model.DictionaryEntry;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DictionarySearchService {
    private static final Logger LOG = Logger.getLogger(DictionarySearchService.class);
    private final DictionaryEntryDao dictionaryEntryDao;

    public Collection<DictionaryEntry> search(Collection<String> terms) throws IOException {
        Collection<DictionaryEntry> result = new ArrayList<>();
        MultiSearchResponse response = dictionaryEntryDao.search(terms);
        for (MultiSearchResponse.Item item : response.getResponses()) {
            for (SearchHit hit : item.getResponse().getHits().getHits()) {
                Map<String, Object> sourceMap = hit.getSourceAsMap();
                Collection<DictionaryEntry> fuzzyEntries = new ArrayList<>();
                fuzzyEntries.add(extractDictionaryEntry(sourceMap, hit.getIndex()));
                DictionaryEntry extractedDE = new DictionaryEntry();
                extractedDE.setTerm(terms.iterator().next());
                extractedDE.setDefinition(fuzzyEntries.stream().flatMap(e -> e.getDefinition()).collect(Collectors.toList()));

                for (String t : terms) {
                    if (extractedDE.getTerm().toLowerCase().contains(t.toLowerCase())) {
                        extractedDE.setTerm(t);
                    }
                }
                result.add(extractedDE);
                break;
            }
        }
        removeDuplicates(result);
        return result;
    }

    public Collection<DictionaryEntry> elasticSearchOneTerm(String term) throws IOException {
        SearchResponse searchResponse = dictionaryEntryDao.elasticSearchOneTerm(term);
        Collection<DictionaryEntry> res = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            DictionaryEntry de = extractDictionaryEntry(hit.getSourceAsMap(), term);
            de.setTerm(term);
            res.add(de);
        }

        removeDuplicates(res);
        return res;
    }

    private void removeDuplicates(Collection<DictionaryEntry> dictionaryEntries) {
        List<DictionaryEntry> toRemove = new ArrayList<>();
        for (DictionaryEntry de : dictionaryEntries) {
            for (DictionaryEntry de2 : dictionaryEntries) {
                if (de.getTerm().equals(de2.getTerm()) && !de.getId().equals(de2.getId())
                        && !toRemove.contains(de2) && !toRemove.contains(de)) {
                    de.getDefinition().addAll(de2.getDefinition());
                    toRemove.add(de2);
                }
            }
        }
        dictionaryEntries.removeAll(toRemove);
    }

    private DictionaryEntry extractDictionaryEntry(Map<String, Object> sourceMap, String term) {
        DictionaryEntry result = new DictionaryEntry();
        if (sourceMap.containsKey("id")) {
            String id = (String) sourceMap.get("id");
            if (sourceMap.containsKey("term")) {
                String termFound = (String) sourceMap.get("term");
                if (sourceMap.containsKey("definition")) {
                    Collection definitionFound = (Collection) sourceMap.get("definition");
                    if (!termFound.isEmpty() && !definitionFound.isEmpty() && !id.isEmpty()) {
                        DictionaryEntry de = new DictionaryEntry();
                        de.setId(id);
                        de.setTerm(termFound);
                        de.setDefinition(definitionFound);
                        return de;
                    }
                }
            }
        }
        return result;
    }

}
