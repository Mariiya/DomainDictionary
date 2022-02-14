package com.domaindictionary.elasticsearch.api;

import com.domaindictionary.elasticsearch.model.DictionaryEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
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
    private ObjectMapper mapper;
    private RestHighLevelClient client;

    public SearchManager(ObjectMapper mapper, RestHighLevelClient client) {
        this.mapper = mapper;
        this.client = client;
    }

    public Collection<DictionaryEntry> search(Collection<String> term, BigInteger resourceId) {
        return null;
    }

    public DictionaryEntry search(String term, BigInteger resourceId) throws IOException {
        SearchRequest searchRequest = new SearchRequest(DictionaryEntry.getIndex());

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

     /*   QueryBuilder esQuery = QueryBuilders.multiMatchQuery(DictionaryEntry.getIndex(),
                        "term",
                        term,
                        "resourceId",
                        resourceId.toString())
                .operator(Operator.AND);*/

        QueryBuilder query = QueryBuilders.boolQuery()
                .must(
                        QueryBuilders.matchQuery("term", term)
                )
                .should(
                        QueryBuilders.matchQuery("resourceId", resourceId)
                );

        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceMap = hit.getSourceAsMap();
            String id = (String) sourceMap.get("id");
            String termFound = (String) sourceMap.get("term");
            Collection definitionFound = (Collection) sourceMap.get("definition");
            BigInteger resourceIdFound = new BigInteger(sourceMap.get("resourceId").toString());
            if (!termFound.isEmpty() && !definitionFound.isEmpty() && !id.isEmpty()) {
                return new DictionaryEntry(id, termFound, definitionFound, resourceIdFound);
            }
        }
        return null;
    }

    protected List<DictionaryEntry> searchWithOutResource(Collection<String> terms) {
        return Collections.EMPTY_LIST;
    }
}
