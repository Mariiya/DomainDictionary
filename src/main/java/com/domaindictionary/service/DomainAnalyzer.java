package com.domaindictionary.service;

import com.domaindictionary.model.DictionaryEntry;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DomainAnalyzer {

    private final BabelNet babelNet;

    public DomainAnalyzer() throws Exception {
        com.pullenti.unisharp.Stopwatch sw = com.pullenti.unisharp.Utils.startNewStopwatch();
        com.pullenti.Sdk.initializeAll();
        sw.stop();
        BabelNetConfiguration babelNetConfiguration = BabelNetConfiguration.builder()
                .withLicenseKey("YOUR_BABELNET_LICENSE_KEY")
                .build();
        babelNet = BabelNet.getInstance(babelNetConfiguration);
    }

    public Collection<DictionaryEntry> analyze(Collection<DictionaryEntry> dictionaryEntries) throws Exception {
        for (DictionaryEntry de : dictionaryEntries) {
            if (de.getDefinition() != null && de.getDefinition().size() > 1) {
                List<String> searchTerms = graphematicAnalysis(de.getTerm());
                List<String> normalizedSearchTerms = morphologicalAnalysis(searchTerms);

                //for multiword term
                StringBuilder builder = new StringBuilder();
                for(String str: normalizedSearchTerms){
                    builder.append(str);
                }
                BabelSynset babelSynset = getSynset(builder.toString());
                filterDictionaryEntry(de, babelSynset);
            }
        }
        return dictionaryEntries;
    }


    public List<BabelSynset> findSynsets(String term) {
        // Find synsets for the given term
        return babelNet.getSynsets(term, BabelPOS.NOUN);
    }


    protected void filterDictionaryEntry(DictionaryEntry entry, BabelSynset synset2) throws Exception {
        Map<String, Double> definitionToCoef = new HashMap<>();

        for (String definition : entry.getDefinition()) {
            double coefSum = 0;
            Set<String> definitionWords = splitDefinitionIntoSet(definition);
            for (String word : definitionWords) {
                BabelSynset synset1 = getSynset(word);
                if (synset1 != null) {
                    coefSum += calculateWuPalmerSimilarity(synset1, synset2);
                }
            }
            definitionToCoef.put(definition, coefSum);
        }
        entry.setDefinition(removeUnUsedDefinitions(definitionToCoef));
    }

    private Set<String> splitDefinitionIntoSet(String definition) throws Exception {
        Collection temp = graphematicAnalysis(terms);
        return morphologicalAnalysis(temp);
    }

    public double calculateWuPalmerSimilarity(BabelSynset synset1, BabelSynset synset2) {
        if (synset1 != null && synset2 != null) {
            return synset1.getWuPalmerSimilarity(synset2);
        } else {
            // Handle the case where one of the terms doesn't have synsets
            return 0.0;
        }
    }

    // Retrieve the synset for a given term
    private BabelSynset getSynset(String term) {
        try {
            return BabelNet.getInstance().getSynsets(term, BabelPOS.NOUN).get(0);
        } catch (IndexOutOfBoundsException e) {
            // Handle the case where the term has no synsets
            return null;
        }
    }

    private Collection<String> removeUnUsedDefinitions(Map<String, Double> definitionToCoef) {
        Collection<String> toRemove = new ArrayList<>();

        for (Map.Entry<String, Double> entry : definitionToCoef.entrySet()) {
            if (entry.getValue() < 1 && definitionToCoef.values().stream()
                    .anyMatch(integer -> integer >= 1
                            && integer == definitionToCoef.values().stream().mapToInt(v -> v).max().getAsInt())) {
                toRemove.add(entry.getKey());
            }
        }
        for (String key : toRemove) {
            definitionToCoef.remove(key);
        }
        return definitionToCoef.keySet();
    }

    protected List<String> graphematicAnalysis(String searchTerms) {
        List<String> terms = Arrays.asList(searchTerms.split("\\s+"));
        List<String> result = new ArrayList<>();
        for (String t : terms) {
            t = t.trim();
            result.add(t);
        }
        return result;
    }

    protected List<String> morphologicalAnalysis(List<String> searchTerms) {
        List<String> result = new ArrayList<>();
        for (String str : searchTerms) {
            result.add(getNormalyzedString(str));
        }
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
