package com.domaindictionary.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.domaindictionary.model.elasticsearch.DictionaryEntryDoc;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeyWordsExtractorService {
    private static final Logger log = LoggerFactory.getLogger(KeyWordsExtractorService.class);

    private final ElasticsearchClient esClient;

    public List<String> extractKeyWords(String text) {
        // Tokenize and normalize
        String[] words = text.split("[\\s,.;:!?()\\[\\]{}\"']+");
        Set<String> uniqueWords = Arrays.stream(words)
                .map(String::trim)
                .filter(w -> w.length() > 2)
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // Remove common stopwords (Ukrainian + English)
        Set<String> stopwords = Set.of(
                "і", "в", "на", "за", "до", "з", "що", "як", "не", "але", "це", "та", "або", "у", "від",
                "the", "a", "an", "is", "are", "was", "were", "be", "been", "and", "or", "but", "in", "on",
                "at", "to", "for", "of", "with", "by", "this", "that", "it", "as", "from", "has", "have"
        );

        List<String> candidates = uniqueWords.stream()
                .filter(w -> !stopwords.contains(w))
                .toList();

        // Try to match against existing dictionary entries in ES
        List<String> keyTerms = new ArrayList<>();
        for (String candidate : candidates) {
            try {
                SearchResponse<DictionaryEntryDoc> response = esClient.search(s -> s
                        .index(DictionaryEntryDoc.INDEX)
                        .size(1)
                        .query(q -> q.match(m -> m.field("term").query(candidate))),
                        DictionaryEntryDoc.class);
                if (response.hits().total() != null && response.hits().total().value() > 0) {
                    Hit<DictionaryEntryDoc> hit = response.hits().hits().get(0);
                    if (hit.source() != null) {
                        keyTerms.add(hit.source().getTerm());
                        continue;
                    }
                }
            } catch (Exception e) {
                log.debug("ES lookup failed for '{}': {}", candidate, e.getMessage());
            }
            // Still add as candidate keyword
            keyTerms.add(candidate);
        }

        return keyTerms.stream().distinct().limit(50).toList();
    }
}
