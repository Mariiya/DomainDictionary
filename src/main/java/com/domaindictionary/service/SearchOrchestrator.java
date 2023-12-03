package com.domaindictionary.service;

import com.domaindictionary.dao.DictionaryDao;
import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.model.Relation;
import com.domaindictionary.model.Thesaurus;
import com.domaindictionary.model.ThesaurusEntry;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;


@Service("searchProcessManager")
@AllArgsConstructor
public class SearchOrchestrator {
    private final DictionarySearchService searchService;
    private final ExternalResourceSearchService externalResourceSearchService;
    private final DomainAnalyzer analysisService;

    public Collection<DictionaryEntry> search(List<String> terms, boolean isAnalyzeEnable) throws Exception {
       Collection<DictionaryEntry> result = searchService.search(terms);

        addEmptyDefinitionTerms(terms, result);
        //Analyze results
        splitDefinitions(result);
        if (isAnalyzeEnable) {
            analysisService.analyze(result);
        }
        return result;
    }

    public List<ThesaurusEntry> searchRelations(List<String> terms) {
        List<ThesaurusEntry> result = new ArrayList<>();
        for(String term: terms){
            ThesaurusEntry entry = new ThesaurusEntry();
            entry.setTerm(term);
            Relation relation = new Relation();
            relation.setSynsets(analysisService.findSynsets(term)
                    .stream().map(s->s.toString()).collect(Collectors.toList()));
            entry.setRelations(Collections.singletonList(relation));

            result.add(entry);
        }
        return result;
    }
    protected void splitDefinitions(Collection<DictionaryEntry> dictionaryEntries) {
        for (DictionaryEntry de : dictionaryEntries) {
            if (de != null && de.getDefinition() != null && de.getDefinition().size() == 1) {
                String definition = de.getDefinition().iterator().next();
                String[] definitions = definition.split(" \\d{1,2}\\.");//rule.getDefinitionSeparator());
                List<String> toList = Arrays.asList(definitions);
                if (toList.size() > 2) {
                    toList = toList.subList(1, toList.size());
                }
                de.setDefinition(toList);
            }
        }
    }

    protected void addEmptyDefinitionTerms(Collection<String> terms, Collection<DictionaryEntry> dictionaryEntries) {
        for (String t : terms) {
            boolean isDe = false;
            for (DictionaryEntry de : dictionaryEntries) {
                if (de.getTerm().equalsIgnoreCase(t)) {
                    isDe = true;
                    break;
                }
            }
            if (!isDe) {
                dictionaryEntries.add(new DictionaryEntry(null, t, Collections.emptyList(), BigInteger.ONE));
            }
        }
    }

}
