package com.domaindictionary.service;

import com.domaindictionary.dto.SearchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
public class ExternalResourceSearchService {
    private static final Logger log = LoggerFactory.getLogger(ExternalResourceSearchService.class);
    private final ClaudeApiService claudeApiService;

    public ExternalResourceSearchService(ClaudeApiService claudeApiService) {
        this.claudeApiService = claudeApiService;
    }

    public SearchResult search(String term) {
        // 1. Try Wikipedia first
        SearchResult wikiResult = searchWikipedia(term);
        if (wikiResult != null) {
            return wikiResult;
        }

        // 2. Fallback to Claude API
        return searchViaClaude(term);
    }

    private SearchResult searchWikipedia(String term) {
        try {
            String encoded = URLEncoder.encode(term, StandardCharsets.UTF_8);
            // Try Ukrainian Wikipedia first, then English
            for (String lang : List.of("uk", "en")) {
                String url = "https://" + lang + ".wikipedia.org/wiki/" + encoded;
                Document doc = Jsoup.connect(url)
                        .userAgent("DomainDictionary/1.0")
                        .timeout(5000)
                        .get();
                Element firstParagraph = doc.select("#mw-content-text .mw-parser-output > p:not(.mw-empty-elt)").first();
                if (firstParagraph != null) {
                    String text = firstParagraph.text().trim();
                    if (!text.isEmpty() && text.length() > 20) {
                        return SearchResult.builder()
                                .term(term)
                                .definitions(List.of(text))
                                .source("wikipedia-" + lang)
                                .build();
                    }
                }
            }
        } catch (Exception e) {
            log.debug("Wikipedia search failed for '{}': {}", term, e.getMessage());
        }
        return null;
    }

    private SearchResult searchViaClaude(String term) {
        try {
            String definition = claudeApiService.getDefinition(term);
            if (definition != null && !definition.isBlank()) {
                return SearchResult.builder()
                        .term(term)
                        .definitions(List.of(definition))
                        .source("claude-api")
                        .build();
            }
        } catch (Exception e) {
            log.warn("Claude API search failed for '{}': {}", term, e.getMessage());
        }
        return SearchResult.builder()
                .term(term)
                .definitions(Collections.emptyList())
                .source("not_found")
                .build();
    }
}
