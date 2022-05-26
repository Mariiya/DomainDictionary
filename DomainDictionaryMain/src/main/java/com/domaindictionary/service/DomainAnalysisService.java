package com.domaindictionary.service;

import com.domaindictionary.model.DictionaryEntry;
import com.domaindictionary.model.DomainDictionary;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DomainAnalysisService {

    public DomainAnalysisService() throws Exception {
        com.pullenti.unisharp.Stopwatch sw = com.pullenti.unisharp.Utils.startNewStopwatch();
        com.pullenti.Sdk.initializeAll();
        sw.stop();
    }

    public Collection<DictionaryEntry> analyze(Collection<DictionaryEntry> dictionaryEntries, Collection<String> searchTerms) throws Exception {
        searchTerms = graphematicAnalysis(searchTerms);
        Set<String> normalizedSearchTerms = morphologicalAnalysis(searchTerms);

        for (DictionaryEntry de : dictionaryEntries) {
            if (de.getDefinition() != null)
                filterDictionaryEntry(de, normalizedSearchTerms);
        }
        return dictionaryEntries;
    }

    public DomainDictionary analyze(DomainDictionary dd, String stylisticZone) {
        return dd;
    }

    protected void filterDictionaryEntry(DictionaryEntry entry, Collection<String> searchTerms) throws Exception {
        Map<String, Integer> definitionToCount = new HashMap<>();
        for (String definition : entry.getDefinition()) {
            int count = getNumberOfMatches(getNormalyzedString(definition), searchTerms);
            definitionToCount.put(definition, count);
        }
        entry.setDefinition(removeUnUsedDefinitions(definitionToCount));
    }

    private Collection<String> removeUnUsedDefinitions(Map<String, Integer> definitionToCount) {
        Collection<String> toRemove = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : definitionToCount.entrySet()) {
            if (entry.getValue() < 1 && definitionToCount.values().stream()
                    .anyMatch(integer -> integer >= 1
                            && integer == definitionToCount.values().stream().mapToInt(v -> v).max().getAsInt())) {
                toRemove.add(entry.getKey());
            }
        }
        for (String key : toRemove) {
            definitionToCount.remove(key);
        }
        return definitionToCount.keySet();
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
            if (word != null && (word.equals(term) || word.contains(term))) {
                return true;
            }
        }
        return false;
    }

    protected Collection<String> graphematicAnalysis(Collection<String> searchTerms) {
        Collection<String> result = new ArrayList<>();
        for (String t : searchTerms) {
            t = t.trim();
            if (t.contains(" ")) {
                result.addAll(Arrays.asList(t.split(" ")));
            } else {
                result.add(t);
            }
        }

        return result;
    }

    protected Set<String> morphologicalAnalysis(Collection<String> searchTerms) throws Exception {
        Set<String> result = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : searchTerms) {
            stringBuilder.append(str).append(" ");
        }
        result.addAll(Arrays.asList(getNormalyzedString(stringBuilder.toString()).split(" ")));
        result.addAll(searchTerms);
        return result;
    }

    protected String getNormalyzedString(String txt) {
        StringBuilder result = new StringBuilder();
        com.pullenti.ner.AnalysisResult are = com.pullenti.ner.ProcessorService.getEmptyProcessor().process(new com.pullenti.ner.SourceOfAnalysis(txt), null, null);
        for (com.pullenti.ner.Token t = are.firstToken; t != null; t = t.getNext()) {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0, null);
            if (npt == null)
                continue;
            t = npt.getEndToken();
        }
        try (com.pullenti.ner.Processor proc = com.pullenti.ner.ProcessorService.createProcessor()) {
            com.pullenti.ner.AnalysisResult ar = proc.process(new com.pullenti.ner.SourceOfAnalysis(txt), null, null);
            for (com.pullenti.ner.Token t = ar.firstToken; t != null; t = t.getNext()) {
                if (t.getReferent() != null)
                    continue;
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.ADJECTIVECANBELAST, 0, null);
                if (npt == null)
                    continue;
                t = npt.getEndToken();
            }
        }
        try (com.pullenti.ner.Processor proc = com.pullenti.ner.ProcessorService.createSpecificProcessor(com.pullenti.ner.keyword.KeywordAnalyzer.ANALYZER_NAME)) {
            com.pullenti.ner.AnalysisResult ar = proc.process(new com.pullenti.ner.SourceOfAnalysis(txt), null, null);
            for (com.pullenti.ner.Token t = ar.firstToken; t != null; t = t.getNext()) {
                if (t instanceof com.pullenti.ner.ReferentToken) {
                    com.pullenti.ner.keyword.KeywordReferent kw = com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.keyword.KeywordReferent.class);
                    if (kw == null)
                        continue;
                    String kwstr = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class), com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
                    result.append(kwstr).append(" ");
                }
            }
        }
        return result.toString();
    }

}
