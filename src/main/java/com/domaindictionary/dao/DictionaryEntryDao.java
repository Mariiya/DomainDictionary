package com.domaindictionary.dao;

import com.domaindictionary.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;

@Repository
public class DictionaryEntryDao {

    private final RestHighLevelClient client;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public DictionaryEntryDao(RestHighLevelClient client) {
        this.client = client;
    }

    public void saveOrUpdate(DictionaryEntry entry) throws IOException {
        IndexRequest indexRequest = new IndexRequest(DictionaryEntry.INDEX)
                .id(entry.getId())
                .source(OBJECT_MAPPER.writeValueAsString(entry), XContentType.JSON);

        UpdateRequest updateRequest = new UpdateRequest(DictionaryEntry.INDEX, entry.getId())
                .doc(OBJECT_MAPPER.writeValueAsString(entry), XContentType.JSON)
                .upsert(indexRequest);

        client.update(updateRequest, RequestOptions.DEFAULT);
    }

    public void saveOrUpdate(ThesaurusEntry entry) throws IOException {
        IndexRequest indexRequest = new IndexRequest(ThesaurusEntry.INDEX)
                .id(entry.getId())
                .source(OBJECT_MAPPER.writeValueAsString(entry), XContentType.JSON);

        UpdateRequest updateRequest = new UpdateRequest(ThesaurusEntry.INDEX, entry.getId())
                .doc(OBJECT_MAPPER.writeValueAsString(entry), XContentType.JSON)
                .upsert(indexRequest);

        client.update(updateRequest, RequestOptions.DEFAULT);
    }

    public void delete(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(DictionaryEntry.INDEX, id);
        client.delete(deleteRequest, RequestOptions.DEFAULT);
    }


    public MultiSearchResponse search(Collection<String> terms) throws IOException {
        MultiSearchRequest request = new MultiSearchRequest();
        for (String t : terms) {
            SearchRequest firstSearchRequest = new SearchRequest(DictionaryEntry.INDEX);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("term", t).fuzziness(Fuzziness.AUTO).boost(1.0f)
                    .prefixLength(0).fuzzyTranspositions(true));
            firstSearchRequest.source(searchSourceBuilder);
            request.add(firstSearchRequest);
        }

        return client.msearch(request, RequestOptions.DEFAULT);

    }

    public SearchResponse elasticSearchOneTerm(String term) throws IOException {
        SearchRequest searchRequest = new SearchRequest(DictionaryEntry.INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        QueryBuilder query = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("term", term).fuzziness(Fuzziness.ONE).boost(1.0f)
                        .prefixLength(0).fuzzyTranspositions(true));

        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }
}
