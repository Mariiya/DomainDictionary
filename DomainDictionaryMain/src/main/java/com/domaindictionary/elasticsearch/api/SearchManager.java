package com.domaindictionary.elasticsearch.api;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import org.elasticsearch.index.query.QueryBuilders;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Service
public class SearchManager {
    private static final Logger LOG = Logger.getLogger(SearchManager.class);
    private RestHighLevelClient client;

    public SearchManager(RestHighLevelClient client) {
        this.client = client;
    }

    public Collection<DictionaryEntry> search(Collection<String> terms, BigInteger resourceId) throws IOException {
        Collection<DictionaryEntry> result = new ArrayList<>();
        MultiSearchRequest request = new MultiSearchRequest();
        for (String t : terms) {
            SearchRequest firstSearchRequest = new SearchRequest(DictionaryEntry.getIndex());
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("term", t));
            firstSearchRequest.source(searchSourceBuilder);
            request.add(firstSearchRequest);
        }

        MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
        for (MultiSearchResponse.Item item : response.getResponses()) {
            for (SearchHit hit : item.getResponse().getHits().getHits()) {
                Map<String, Object> sourceMap = hit.getSourceAsMap();
                result.add(extractDictionaryEntry(sourceMap, hit.getIndex(), resourceId));
            }
        }
        return result;
    }

    public DictionaryEntry search(String term, BigInteger resourceId) throws IOException {
        SearchRequest searchRequest = new SearchRequest(DictionaryEntry.getIndex());
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        QueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("term", term))
                .should(QueryBuilders.matchQuery("resourceId", resourceId))
                .filter(QueryBuilders.fuzzyQuery("term", term));

        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            return extractDictionaryEntry(hit.getSourceAsMap(), term, resourceId);
        }
        return new DictionaryEntry(null, term, Collections.EMPTY_LIST, resourceId);
    }

    private DictionaryEntry extractDictionaryEntry(Map<String, Object> sourceMap, String term, BigInteger resourceId) {
        DictionaryEntry result = new DictionaryEntry(null, term, Collections.EMPTY_LIST, resourceId);
        if (sourceMap.containsKey("id")) {
            String id = (String) sourceMap.get("id");
            if (sourceMap.containsKey("term")) {
                String termFound = (String) sourceMap.get("term");
                if (sourceMap.containsKey("definition")) {
                    Collection definitionFound = (Collection) sourceMap.get("definition");
                    BigInteger resourceIdFound = resourceId;
                    if (sourceMap.containsKey("resourceId") && sourceMap.get("resourceId") != null) {
                        resourceIdFound = new BigInteger(sourceMap.get("resourceId").toString());
                    }
                    if (!termFound.isEmpty() && !definitionFound.isEmpty() && !id.isEmpty()) {
                        return new DictionaryEntry(id, term, definitionFound, resourceIdFound);
                    }
                }
            }
        }
        return result;
    }

    protected List<DictionaryEntry> searchWithOutResource(Collection<String> terms) {
        return Collections.EMPTY_LIST;
    }
}
