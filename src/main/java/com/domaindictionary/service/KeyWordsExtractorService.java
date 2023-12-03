package com.domaindictionary.service;

import lombok.AllArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;


import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class KeyWordsExtractorService {

    private final RestHighLevelClient client;
    private final FileService fileService;


    public List<String> getKeyWords(String text) throws IOException {
        return extractKeywordsFromText(text);
    }

    public List<String> getKeyWords(File file) throws IOException {
        // Assuming you have a method to read the contents of the file into a String
        String fileContent = fileService.readFile(file);
        return extractKeywordsFromText(fileContent);
    }

    private List<String> extractKeywordsFromText(String text) throws IOException {
        // Define the index and field you want to search
        String indexName = "your_index_name";
        String fieldName = "your_text_field_name";

        // Create a search request with a simple match query
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(fieldName, text));

        // Highlight the matched terms to get the keywords
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(fieldName);
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        // Execute the search request
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // Extract and return the keywords from the highlighted results
        return extractKeywordsFromSearchResponse(searchResponse);
    }

    private List<String> extractKeywordsFromSearchResponse(SearchResponse searchResponse) {
        List<String> keywords = new ArrayList<>();

        // Iterate through the search hits and extract highlighted terms
        searchResponse.getHits().forEach(hit -> {
            hit.getHighlightFields().forEach((fieldName, highlightField) -> {
                for (Text fragment : highlightField.fragments()) {
                    keywords.add(fragment.toString());
                }
            });
        });

        return keywords;
    }

}

